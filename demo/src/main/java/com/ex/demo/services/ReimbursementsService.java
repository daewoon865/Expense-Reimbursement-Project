package com.ex.demo.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.ex.demo.dto.ReassignReimbursementDTO;
import com.ex.demo.dto.ReimbursementRequestDTO;
import com.ex.demo.entities.ReimbursementRequest;
import com.ex.demo.entities.ReimbursementUpdate;
import com.ex.demo.entities.Session;
import com.ex.demo.helpers.Helpers;
import com.ex.demo.repositories.ReimbursementRequestRepository;
import com.ex.demo.repositories.ReimbursementUpdateRepository;
import com.ex.demo.repositories.SessionRepository;
import com.ex.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReimbursementsService {

    @Autowired
    private UserRepository ur;

    @Autowired
    private SessionRepository sr;

    @Autowired
    private ReimbursementRequestRepository rr;

    @Autowired
    private ReimbursementUpdateRepository rur;

    @Autowired
    private Helpers helpers;

    /**
     * Verifies user access
     * @param token
     * @param employeeId
     * @return
     */
    private Session verifyAccess (String token, int employeeId){
        return sr.findAllByuserId(employeeId).stream()
            .filter(
                x -> x.getExpiry() > new Date().getTime() 
                && x.getToken().equals(token)
            )
            .collect(Collectors.toList())
            .get(0);
    }

    /**
     * Helper to convert reimbursement requests to strings
     * @param lrr
     * @return
     */    
    private String lrrToString (List<ReimbursementRequest> lrr){
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        lrr.forEach(x -> {
            sb.append("\t{\n");
            sb.append("\t\t\"requestId\":\"" + x.getRequestId()+"\",\n");
            sb.append("\t\t\"employeeComment\":\"" + x.getEmployeeComments()+"\",\n");
            sb.append("\t\t\"reimbursementAmount\":\"" + x.getReimbursementAmount()+"\",\n");
            sb.append("\t\t\"updates\":\n");
            sb.append("\t\t[\n");

            rur.findAllByreimbursementId(x.getRequestId()).forEach(y -> {
                sb.append("\t\t\t{\n");
                
                sb.append("\t\t\t\t\t\"managerComments\":\"" + y.getManagerComments()+"\",\n");
                sb.append("\t\t\t\t\t\"managerId\":" + y.getManagerId()+",\n");
                sb.append("\t\t\t\t\t\"status\":\"" + y.getReimbursementStatus()+"\"\n");

                sb.append("\t\t\t},\n");
            });
            sb.deleteCharAt(sb.length()-1);
            if(sb.charAt(sb.length()-1)==','){
                sb.deleteCharAt(sb.length()-1);
            }
            sb.append("\n\t\t]\n");
            sb.append("\t},\n");
        });
        sb.deleteCharAt(sb.length()-2);
        sb.append("]");
        if (sb.toString().equals("\n]")){
            return "[]";
        }
        return sb.toString();
    }

    /**
     * Helper to find employee username by id
     * @param userid
     * @return
     */
    public String findEmployeeUsernameById (int userid){
        return ur.findAllByuserId(userid).get(0).getUsername();        
    }

    /**
     * Submit request implementation
     * @param urdto
     * @return
     */
    public ReimbursementRequest submitRequest (ReimbursementRequestDTO urdto){
        Session userVerify = verifyAccess(urdto.getSessionToken(), urdto.getEmployeeId());

        if (userVerify.getAccess().equals("employee")){
            ReimbursementRequest rrq = new ReimbursementRequest(0, userVerify.getSessionId(), userVerify.getUserId(), urdto.getReimbursementAmount(), urdto.getEmployeeComments());

            rr.save(rrq);

            return rrq;
        }

        return null;        
    }

    /**
     * View all reimbursements implementation
     * @param s
     * @return
     */
    public String viewAllReimbursements (Session s){
        Session user = verifyAccess(s.getToken(), s.getUserId());
        if (user.getAccess().equals("manager")){
            return lrrToString(rr.findAll());
        }
        else if (user.getAccess().equals("employee")){
            String res = lrrToString(rr.findAllByemployeeId(user.getUserId()));
            return res;
        }
        else{
            return null;
        }
    }

    /**
     * Update reimbursement implementation
     * @param reimbu
     * @return
     */
    public ReimbursementUpdate updateReimbursement (ReimbursementUpdate reimbu){
        Session user = verifyAccess(reimbu.getSessionToken(), reimbu.getManagerId());
        if (user.getAccess().equals("manager") &&  (reimbu.getReimbursementStatus().equals("approved") || reimbu.getReimbursementStatus().equals("denied")) )
        {
            rur.save(reimbu);

            String useremail = findEmployeeUsernameById(rr.findAllByrequestId(reimbu.getReimbursementId()).get(0).getEmployeeId());
            helpers.sendMail(useremail, "request " + reimbu.getReimbursementId() +" has been " + reimbu.getReimbursementStatus(), "request has been " + reimbu.getReimbursementStatus());

            return reimbu;
        }
        return null;
    }

    /**
     * Reassign reimbursement implementation
     * @param reimbu
     * @return
     */
    public String reassignReimbursement (ReassignReimbursementDTO reimbu){
        Session user = verifyAccess(reimbu.getSessionToken(), reimbu.getManagerId());
        if (user.getAccess().equals("manager"))
        {
            ReimbursementRequest search = rr.findAllByrequestId(reimbu.getReimbursementId()).get(0);
            ReimbursementRequest rrq = new ReimbursementRequest(0, user.getSessionId(), reimbu.getToEmployeeId(), search.getReimbursementAmount(), "reassigned request "+ reimbu.getReimbursementId() + " from employee "+ search.getEmployeeId()+" - pending");
            rr.save(rrq);

            //Send email to request owner to inform
            String user1email = findEmployeeUsernameById(rr.findAllByrequestId(reimbu.getReimbursementId()).get(0).getEmployeeId());
            helpers.sendMail(user1email, "request " + reimbu.getReimbursementId() +" has been reassigned", "request has been reassigned");

            rur.save(new ReimbursementUpdate(0, reimbu.getSessionToken(), reimbu.getManagerId(), reimbu.getReimbursementId(), "reassigned", reimbu.getComments()));

            //Send email to new request owner
            String user2email = findEmployeeUsernameById(reimbu.getToEmployeeId());
            helpers.sendMail(user2email, "request " + reimbu.getReimbursementId() +" has been reassigned from another user", "request has been reassigned from another user");

            return "success";
        }
        return "failed";
    }


}

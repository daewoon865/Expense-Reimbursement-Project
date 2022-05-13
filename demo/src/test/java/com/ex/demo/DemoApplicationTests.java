package com.ex.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ex.demo.dto.ReassignReimbursementDTO;
import com.ex.demo.dto.ReimbursementRequestDTO;
import com.ex.demo.entities.ReimbursementRequest;
import com.ex.demo.entities.ReimbursementUpdate;
import com.ex.demo.entities.Session;
import com.ex.demo.entities.User;
import com.ex.demo.helpers.Helpers;
import com.ex.demo.repositories.ReimbursementRequestRepository;
import com.ex.demo.repositories.ReimbursementUpdateRepository;
import com.ex.demo.repositories.SessionRepository;
import com.ex.demo.repositories.UserRepository;
import com.ex.demo.services.ReimbursementsService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Mock
    private UserRepository ur;

    @Mock
    private SessionRepository sr;

    @Mock
    private ReimbursementRequestRepository rr;

    @Mock
    private ReimbursementUpdateRepository rur;

	@Mock
	private Helpers helpers;

	@InjectMocks
	ReimbursementsService reimbursementsService;

	private static final long date = new Date().getTime();

	@BeforeEach
	public void setUp(){
		//New user
		User manager = new User(1, "manager", "daewoon865@revature.net", "testpass12345");
		User employee = new User(2, "employee", "daewoon865+1@revature.net", "testpass12345");

		List <User> users = new ArrayList<User>();
		users.add(employee);
		users.add(manager);

		List <User> employees = new ArrayList<User>();
		employees.add(employee);

		List <User> managers = new ArrayList<User>();
		employees.add(manager);

		Mockito.when(ur.findAll()).thenReturn(users);
		Mockito.when(ur.findAllByuserId(employee.getUserId())).thenReturn(employees);
		Mockito.when(ur.findAllByuserId(manager.getUserId())).thenReturn(managers);

		
		//New session
		Session managerSession = new Session(1,"manager",1, date+(60*60*1000), "123");
		Session employeeSession = new Session(2,"employee",2, date+(60*60*1000), "1234");

		List <Session> sessions = new ArrayList<Session>();
		sessions.add(managerSession);
		sessions.add(employeeSession);

		List<Session> eSessions = new ArrayList<Session>();
		eSessions.add(employeeSession);

		List<Session> mSessions = new ArrayList<Session>();
		mSessions.add(managerSession);

		Mockito.when(sr.findAll()).thenReturn(sessions);
		Mockito.when(sr.findAllByuserId(manager.getUserId())).thenReturn(mSessions);
		Mockito.when(sr.findAllByuserId(employee.getUserId())).thenReturn(eSessions);

		//Check reimbursement return
		List<ReimbursementRequest> rbul = new ArrayList<ReimbursementRequest>();
		rbul.add(new ReimbursementRequest(1, 2, 2,  1234, "test"));
		
		Mockito.when(rr.findAllByemployeeId(2)).thenReturn(rbul);

		//Manager reimbursement update
		ReimbursementUpdate testRU = new ReimbursementUpdate(1, "123", 1, 1, "approved", "approved test");
		Mockito.when(rur.save(testRU)).thenReturn(testRU);

		Mockito.when(rr.findAllByrequestId(1)).thenReturn(rbul);

		Mockito.doNothing().when(helpers).sendMail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

		//Manager reassign update
	}

	@Test
	void contextLoads() {
	}

	@Test
	void userSubmitReimbursement(){
		ReimbursementRequestDTO urdto = new ReimbursementRequestDTO ("1234", 2, 123, "submit request");
		reimbursementsService.submitRequest(urdto);

		ReimbursementRequestDTO badurdto = new ReimbursementRequestDTO ("12", 2, 123, "submit request");
		Assertions.assertThrows(Exception.class, () -> reimbursementsService.submitRequest(badurdto));
	}

	@Test
	void viewAllReimbursements(){
		long date = new Date().getTime();
		reimbursementsService.viewAllReimbursements(new Session(2,"employee",2, date+(60*60*1000), "1234"));

		reimbursementsService.viewAllReimbursements(new Session(1,"manager",1, date+(60*60*1000), "123"));

		Assertions.assertThrows(Exception.class, () -> reimbursementsService.viewAllReimbursements(new Session(1,"employee",3, date+(60*60*1000), "1234")));
	}

	@Test
	void updateReimbursement(){
		ReimbursementUpdate testRU = new ReimbursementUpdate(1, "123", 1, 1, "approved", "approved test");
		Assertions.assertEquals(testRU,reimbursementsService.updateReimbursement(testRU));
		ReimbursementUpdate badRU = new ReimbursementUpdate(1, "1234", 1, 1, "approved", "approved test");
		Assertions.assertThrows(Exception.class, () -> reimbursementsService.updateReimbursement(badRU));
	}

	@Test
	void reassignReimbursement(){
		ReassignReimbursementDTO reimbu = new ReassignReimbursementDTO ("123", 1, 1, 2, "reassigning test");
		reimbursementsService.reassignReimbursement(reimbu);

		ReassignReimbursementDTO reimbunotexist = new ReassignReimbursementDTO ("123", 1, 2, 2, "reassigning test");
		Assertions.assertThrows (Exception.class, () -> reimbursementsService.reassignReimbursement(reimbunotexist));

		ReassignReimbursementDTO badreimbu = new ReassignReimbursementDTO ("1234", 1, 1, 2, "reassigning test");
		Assertions.assertThrows(Exception.class, () -> reimbursementsService.reassignReimbursement(badreimbu));
	}


}

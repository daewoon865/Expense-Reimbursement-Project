package com.ex.demo.controller;


import com.ex.demo.dto.ReassignReimbursementDTO;
import com.ex.demo.dto.ReimbursementRequestDTO;

import com.ex.demo.entities.ReimbursementUpdate;
import com.ex.demo.entities.Session;
import com.ex.demo.services.ReimbursementsService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reimbursements")
public class ReimbursementsController {

    @Autowired
    ReimbursementsService business;

    /**
     * Creates a reimbursement request
     * @param urdto
     * @return
     */
    @PostMapping("/submit")
    public ResponseEntity submitRequest(@RequestBody ReimbursementRequestDTO urdto){
        try {
            return ResponseEntity.ok().body(business.submitRequest(urdto));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Something went wrong submitting a request");
        }
    }

    /**
     * View all requests if manager, requests for user if employee
     * @param s
     * @return
     */
    @GetMapping("/view-reimbursements")
    public ResponseEntity viewAllReimbursements(@RequestBody Session s){
        try {
            return ResponseEntity.ok().body(business.viewAllReimbursements(s));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Something went wrong trying to view reimbursements");
        }
    }
    
    //approved or denied
    /**
     * Adds a approved or denied status to a reimbursement request
     * @param reimbu
     * @return
     */
    @PostMapping ("/update-reimbursement")
    public ResponseEntity updateReimbursement (@RequestBody ReimbursementUpdate reimbu){
        try {
            return ResponseEntity.ok(business.updateReimbursement(reimbu));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Something went wrong trying to update reimbursements");
        }
    }

    //Update -> Add reassigned status, and create a new a new request for the new user
    /**
     * Handles reimbursement reassignment request
     * @param reimbu
     * @return
     */
    @PostMapping ("/reassign-reimbursement")
    public ResponseEntity reassignReimbursement (@RequestBody ReassignReimbursementDTO reimbu){
        try {
            return ResponseEntity.ok().body(business.reassignReimbursement(reimbu));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Something went wrong trying to reassign reimbursements");
        }
    }
}

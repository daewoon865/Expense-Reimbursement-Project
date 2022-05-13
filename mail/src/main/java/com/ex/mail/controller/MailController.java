package com.ex.mail.controller;

import com.ex.mail.dto.MailRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.*;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that sends mail 
 */
@RestController
public class MailController {

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/sendMail")
    public ResponseEntity sendMail (@RequestBody MailRequest mr){
        try {
            SimpleMailMessage message = new SimpleMailMessage(); 
            message.setFrom("daewoonkim@outlook.com");
            message.setTo(mr.getMailTo()); 
            message.setSubject(mr.getSubject()); 
            message.setText(mr.getMessage());
            emailSender.send(message);
            return ResponseEntity.ok().body("success sending mail");     
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("something went wrong sending mail");
        }
    }


}

package com.ex.demo.controller;

import java.time.Instant;
import java.util.stream.Collectors;

import com.ex.demo.dto.UserAuthenticate;
import com.ex.demo.entities.Session;
import com.ex.demo.entities.User;
import com.ex.demo.repositories.SessionRepository;
import com.ex.demo.repositories.UserRepository;

import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Setter;

@RestController
public class SessionController {
    @Setter(onMethod =@__({@Autowired}) )
    private UserRepository ur;

    @Setter(onMethod =@__({@Autowired}) )
    private SessionRepository sr;

    /**
     * User registration function
     * @param cu
     * @return
     */
    @PostMapping("/create-employee")
    public ResponseEntity createEmployee(@RequestBody UserAuthenticate cu){
        try {
            cu.setUserType("employee");
            ur.save(new User(cu));
            return ResponseEntity.ok("created employee");
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error creating user");
        }
    }

    /**
     * User login response
     * @param cu
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody UserAuthenticate cu){
        try {
            User user = ur.findAllByusername(cu.getUsername()).stream()
                .filter(x -> x.getUserpass().equals(cu.getUserpass()))
                .collect(Collectors.toList()).get(0);


            long gentoken = Instant.now().toEpochMilli();

            long expiry = gentoken + (15 * 60 * 1000);

            Session s = new Session(0, user.getUserType(), user.getUserId(), expiry, String.valueOf(gentoken));
            sr.save(s);

            return ResponseEntity.ok().body(s);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error logging in");
        }
    }

}

package com.ex.demo.entities;

import lombok.*;
import javax.persistence.*;

import com.ex.demo.dto.UserAuthenticate;

@Entity
@Table(name="Users")
@Getter
@Setter
@NoArgsConstructor
@ToString

@EqualsAndHashCode
@AllArgsConstructor
/**
 * Represents user in db
 */
public class User {

    @Id
    @Column(name="userId", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String userType;

    private String username;
    
    private String userpass;

    public User(UserAuthenticate cu){
        this.username = cu.getUsername();
        this.userpass = cu.getUserpass();
        this.userType = cu.getUserType();
    }

}

package com.ex.demo.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table (name="Sessions")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
/**
 * Represents session entity in db
 */
public class Session {

    @Id
    @Column(name="sessionId", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sessionId;

    private String access;

    private int userId;

    private long expiry;

    private String token;

}

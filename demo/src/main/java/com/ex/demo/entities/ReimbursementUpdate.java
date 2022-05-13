package com.ex.demo.entities;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name="ReimbursementUpdate")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
/**
 * Represents reimbursement update entity in db
 */
public class ReimbursementUpdate {

    @Id
    @Column(name="updateId", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int updateId;

    private String sessionToken;

    private int managerId;

    private int reimbursementId;

    private String reimbursementStatus;

    private String managerComments;
}

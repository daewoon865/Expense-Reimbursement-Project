package com.ex.demo.entities;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name="ReimbursementRequest")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
/**
 * Represents Reimbursement request in db
 */
public class ReimbursementRequest {

    @Id
    @Column(name="requestId", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;

    private int sessionId;

    private int employeeId;

    private long reimbursementAmount;

    private String employeeComments;

}

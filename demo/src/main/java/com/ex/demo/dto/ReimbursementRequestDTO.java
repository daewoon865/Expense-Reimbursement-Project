package com.ex.demo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * Represents reimbursement request from employee
 */
public class ReimbursementRequestDTO {
    private String sessionToken;

    private int employeeId;

    private long reimbursementAmount;
 
    private String employeeComments;
}

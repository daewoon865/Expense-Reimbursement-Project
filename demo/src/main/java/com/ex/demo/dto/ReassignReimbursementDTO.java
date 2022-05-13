package com.ex.demo.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
/**
 * Represents Reassign reimbursement request
 */
public class ReassignReimbursementDTO {
    private String sessionToken;

    private int managerId;

    private int reimbursementId;

    private int toEmployeeId;

    private String comments;
}

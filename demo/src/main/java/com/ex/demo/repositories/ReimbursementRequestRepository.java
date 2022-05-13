package com.ex.demo.repositories;

import java.util.List;

import com.ex.demo.entities.ReimbursementRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for reimbursement requests
 */
@Repository
public interface ReimbursementRequestRepository extends JpaRepository<ReimbursementRequest, Integer> {
    List <ReimbursementRequest> findAllByemployeeId(int employeeId);

    List <ReimbursementRequest> findAllByrequestId (int requestId);
}

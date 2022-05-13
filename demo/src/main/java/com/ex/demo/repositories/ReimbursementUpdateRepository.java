package com.ex.demo.repositories;

import java.util.List;

import com.ex.demo.entities.ReimbursementUpdate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository for reimbursement updates
 */
@Repository
public interface ReimbursementUpdateRepository extends JpaRepository<ReimbursementUpdate, Integer> {
    List <ReimbursementUpdate> findAllByreimbursementId(int reimbursementId); 
}

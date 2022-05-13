package com.ex.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.ex.demo.entities.Session;


/**
 * Repository for sessions
 */
@Repository
public interface SessionRepository extends JpaRepository <Session, Integer>{

    List<Session> findAllByuserId(int userId);

}

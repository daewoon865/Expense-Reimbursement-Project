package com.ex.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.ex.demo.entities.User;


/**
 * Repository for users
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

    List<User> findAllByusername (String username);

    List<User> findAllByuserId (int userId);
}

package com.example.VotingApp.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VotingApp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
 User findByUsername(String username);
   // Optional<User> findByUsername(String username);
   
}
package com.example.VotingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VotingApp.entity.Poll;

public interface PollRepository extends JpaRepository<Poll, Long> {
}
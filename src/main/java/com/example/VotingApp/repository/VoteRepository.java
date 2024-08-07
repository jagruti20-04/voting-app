package com.example.VotingApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VotingApp.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
	List<Vote> findByPollId(Long pollId);
}
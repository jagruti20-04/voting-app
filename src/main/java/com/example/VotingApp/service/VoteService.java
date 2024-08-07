package com.example.VotingApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.VotingApp.entity.Vote;
import com.example.VotingApp.repository.VoteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Transactional
    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    public List<Vote> getVotesByPollId(Long pollId) {
        return voteRepository.findByPollId(pollId);
    }

    public Vote getVoteById(Long id) {
        return voteRepository.findById(id).orElse(null);
    }

    @Transactional
    public Vote updateVote(Long id, Vote updatedVote) {
        Optional<Vote> optionalVote = voteRepository.findById(id);
        if (optionalVote.isPresent()) {
            Vote existingVote = optionalVote.get();
            existingVote.setChoice(updatedVote.getChoice());
            existingVote.setVoteDate(updatedVote.getVoteDate());
            existingVote.setConfirmed(updatedVote.isConfirmed());
            // Update other fields as necessary
            return voteRepository.save(existingVote);
        } else {
            throw new RuntimeException("Vote not found with id " + id);
        }
    }

    @Transactional
    public void deleteVote(Long id) {
        voteRepository.deleteById(id);
    }
}

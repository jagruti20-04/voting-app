package com.example.VotingApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.VotingApp.entity.Poll;
import com.example.VotingApp.repository.PollRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    public Poll savePoll(Poll poll) {
        if (poll.getCreatedDate() == null) {
            poll.setCreatedDate(LocalDateTime.now());
        }
        return pollRepository.save(poll);
    }
    public List<Poll> getAllPolls() {
        List<Poll> polls = pollRepository.findAll();
        polls.forEach(poll -> System.out.println(poll)); // Debug statement
        return polls;
    }

    public Poll getPollById(Long id) {
        return pollRepository.findById(id).orElse(null);
    }

    public Poll updatePoll(Long id, Poll updatedPoll) {
        Optional<Poll> optionalPoll = pollRepository.findById(id);
        if (optionalPoll.isPresent()) {
            Poll existingPoll = optionalPoll.get();
            existingPoll.setTitle(updatedPoll.getTitle());
            existingPoll.setDescription(updatedPoll.getDescription());
//            existingPoll.setImageFile(updatedPoll.getImage());
            existingPoll.setCreatedDate(updatedPoll.getCreatedDate());
            existingPoll.setEndDate(updatedPoll.getEndDate());
            existingPoll.setIsActive(updatedPoll.getIsActive());
            // Update other fields as necessary
            return pollRepository.save(existingPoll);
        } else {
            throw new RuntimeException("Poll not found with id " + id);
        }
    }

    public void deletePoll(Long id) {
        pollRepository.deleteById(id);
    }
	public Optional<Poll> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.of(pollRepository.findById(id).orElse(null));
	}
}

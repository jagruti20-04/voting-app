package com.example.VotingApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.VotingApp.entity.Poll;
import com.example.VotingApp.entity.User;
import com.example.VotingApp.entity.Vote;
import com.example.VotingApp.service.PollService;
import com.example.VotingApp.service.UserService;
import com.example.VotingApp.service.VoteService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private PollService pollService;

    @GetMapping("/vote")
    public String showVoteForm(@RequestParam("pId") Long pollId, Model model) {
        Optional<Poll> pollOptional = pollService.findById(pollId);
       
        if (!pollOptional.isPresent()) {
        	
            return "redirect:/error"; // Handle the case where the poll does not exist
           
        }
       
        Vote vote = new Vote();
        vote.setPoll(pollOptional.get());
       
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.getUserByUsername(authentication.getName());
           
            System.out.println("Authenticated user: " + user);
             vote.setUser(user);
           
           
        } else {
        	
            return "redirect:/error"; // Handle the case where the user is not authenticated
        }
        
        model.addAttribute("vote", vote);
        
        return "voteForm"; 
    }

    @PostMapping("/vote")
    public String submitVote(@ModelAttribute("vote") Vote vote, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            return "voteForm";
        }
        
        // Retrieve the User based on username from the form
        String username = vote.getUsername();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return "redirect:/error"; // Handle case where user does not exist
        }
        
        // Set user and other properties
        vote.setUser(user);
        vote.setVoteDate(new Date(System.currentTimeMillis()));
        vote.setConfirmed(true);

        voteService.saveVote(vote);
        return "redirect:/polls/all";
    }
    @GetMapping("/success")
    public String successPage() {
        return "succeess"; // Ensure this matches the filename of your success page Thymeleaf template
    }
    
    @GetMapping("/list")
    public String listVotes(Model model) {
        // Fetch all polls
        List<Poll> polls = pollService.getAllPolls();
        model.addAttribute("polls", polls);
        return "votelist";
    }

    @GetMapping("/poll/{id}")
    public String viewVotesForPoll(@PathVariable("id") Long pollId, Model model) {
        // Fetch poll by ID
        Poll poll = pollService.getPollById(pollId);
        if (poll != null) {
            // Fetch votes associated with the poll
            List<Vote> votes = voteService.getVotesByPollId(pollId);
            model.addAttribute("poll", poll);
            model.addAttribute("votes", votes);
            return "pollVotes";
        } else {
            return "redirect:/votes/list";
        }
    }
}

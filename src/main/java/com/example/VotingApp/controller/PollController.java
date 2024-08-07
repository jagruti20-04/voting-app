package com.example.VotingApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.VotingApp.entity.Poll;
import com.example.VotingApp.service.PollService;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @GetMapping("/create")
    public String showPollForm(Model model) {
        model.addAttribute("poll", new Poll());
        return "pollForm";
    }

    @PostMapping("/create")
    public String createPoll(@Valid @ModelAttribute Poll poll, BindingResult bindingResult,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "pollForm";
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            poll.setImage(imageFile.getBytes());
        } else {
            poll.setImage(null);
        }

        pollService.savePoll(poll);
        return "redirect:/polls/all";
    }

    @GetMapping("/all")
  
    public String listPolls(Model model) {
        List<Poll> polls = pollService.getAllPolls();
        if (polls == null) {
            throw new RuntimeException("No polls found.");
        }
        model.addAttribute("polls", polls);
        return "pollList";
    }

    @GetMapping("/{id}")
    public String getPollById(@PathVariable Long id, Model model) {
        Poll poll = pollService.getPollById(id);
        if (poll != null) {
            model.addAttribute("poll", poll);
            return "pollDetails";
        } else {
            return "redirect:/polls/all";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditPollForm(@PathVariable Long id, Model model) {
        Poll poll = pollService.getPollById(id);
        if (poll != null) {
            model.addAttribute("poll", poll);
            return "pollEditForm";
        } else {
            return "redirect:/polls/all";
        }
    }

    @PostMapping("/edit/{id}")
    public String updatePoll(@PathVariable Long id, @Valid @ModelAttribute Poll updatedPoll,
                             @RequestParam(value = "image", required = false) MultipartFile image, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "pollEditForm";
        }

        if (image != null && !image.isEmpty()) {
            updatedPoll.setImage(image.getBytes());
        }

        pollService.updatePoll(id, updatedPoll);
        return "redirect:/polls/all";
    }

    @PostMapping("/delete/{id}")
    public String deletePoll(@PathVariable Long id) {
        pollService.deletePoll(id);
        return "redirect:/polls/all";
    }
    @GetMapping("/imgview/{id}")
    public String listPolls2(@PathVariable Long id, Model model) {
        Poll poll = pollService.getPollById(id);
        if (poll == null) {
            throw new RuntimeException("No poll found.");
        }
        model.addAttribute("poll", poll);
        return "imgview";
    }
}

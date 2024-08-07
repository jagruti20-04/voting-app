package com.example.VotingApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.VotingApp.entity.Poll;
import com.example.VotingApp.entity.User;
import com.example.VotingApp.service.PollService;
import com.example.VotingApp.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
	private final UserService userService;
    private final PollService pollService;

   
   

    public HomeController(PollService pollService, UserService userService) {
        this.pollService = pollService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        // Returns the view name for the home page
        return "index";
    }

    @GetMapping("/view-polls") // Updated mapping
    public String polls(Model model) {
        try {
            List<Poll> polls = pollService.getAllPolls();
            model.addAttribute("polls", polls);
        } catch (Exception e) {
            model.addAttribute("error", "Unable to retrieve polls. Please try again later.");
        }
        return "polls";
    }

    @GetMapping("/users")
    public String users(Model model) {
        try {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
        } catch (Exception e) {
            model.addAttribute("error", "Unable to retrieve users. Please try again later.");
        }
        return "users";
    }

    @GetMapping("/user-register")
    public String userRegister() {
        return "userRegistration";
    }

    @GetMapping("/admin-register")
    public String adminRegister() {
        return "admin-register";
    }

    @GetMapping("/user-login")
    public String userLogin() {
        return "user-login";
    }

    @GetMapping("/admin-login")
    public String adminLogin() {
        return "admin-login";
    }

    @GetMapping("/privacy-policy")
    public String privacyPolicy() {
        return "privacy-policy";
    }

    @GetMapping("/terms-of-service")
    public String termsOfService() {
        return "terms-of-service";
    }
}
package com.example.VotingApp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;


import com.example.VotingApp.entity.User;
import com.example.VotingApp.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/userRegistration")
   
    public String getAdminStatus(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "userRegistration";
        } else {
        	userService.saveUser(user);
            return "UserIndex";
        }
    }
    

    @GetMapping("/userRegistration")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User()); 
        return "userRegistration"; // The name of the Thymeleaf template
    }

    @GetMapping("/userlist")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userList";
    }


    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
    	Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "editUser";
        } else {
            return "redirect:/users/userlist";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "editUser";
        }
        userService.updateUser(id, user);
        return "redirect:/users/userlist";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users/userlist";
    }
    @GetMapping("/userlogin")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "userlogin";
    }

    @PostMapping("/userlogin")
    public String loginUser(@Valid User user, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Wrong password or ID 1");
            return "userlogin";
        }

        if (userService.authenticate(user.getUsername(), user.getPassword())) {
            session.setAttribute("currentUser", user.getUsername());
            System.out.println("Logged in user: " + user.getUsername());
            return "redirect:/users/UserIndex";
        } else {
            System.out.println("Wrong password or ID");
            model.addAttribute("loginError", "Invalid username or password");
            return "userlogin";
        }
    }
    @GetMapping("/UserIndex")
    public String userIndex(Model model, HttpSession session) {
        String currentUser = (String) session.getAttribute("currentUser");
        System.out.println("Current user from session: " + currentUser);
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
        } else {
            model.addAttribute("loginError", "You need to log in first");
            return "redirect:/userlogin"; // Redirect to login page if user is not logged in
        }
        return "UserIndex";
    }
    @GetMapping("/profile")
    public String showUserProfile(Model model, HttpSession session) {
       
        String username = (String) session.getAttribute("currentUser");

        if (username != null) {
            // Fetch user details
            User user = userService.getUserByUsername(username);

            model.addAttribute("user", user);
            return "profile";
        } else {
            model.addAttribute("loginError", "You need to log in first");
            return "redirect:/userlogin"; // Redirect to login page if user is not logged in
        }
    }

}

package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/welcome")
    public String welcome() {

            return "Welcome to the protected endpoint!";

    }



    @PostMapping("/login")
    public String login(@RequestBody User user) {

            return "Login successful!";

    }
}
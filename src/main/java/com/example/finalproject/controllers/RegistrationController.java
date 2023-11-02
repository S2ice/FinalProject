package com.example.finalproject.controllers;

import com.example.finalproject.models.Role;
import com.example.finalproject.models.User;
import com.example.finalproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApiUsers userController;

    @GetMapping("/registration")
    private String RegView(){
        return "registration";
    }

    @PostMapping("/registration")
    private String registerUser(User user, Model model) {
        ResponseEntity<User> response = userController.registerUser(user);

        if (response.getStatusCode() == HttpStatus.OK) {
            return "redirect:/login";
        } else {
            model.addAttribute("message", "Произошла ошибка при регистрации.");
            return "registration";
        }
    }
}

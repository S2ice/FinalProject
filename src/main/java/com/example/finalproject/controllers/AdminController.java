package com.example.finalproject.controllers;

import com.example.finalproject.models.User;
import com.example.finalproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {

    UserRepository userRepository;

    @Autowired
    private ApiUsers userController;

    @GetMapping("")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/user")
    public String adminUserPage(Model model) {
        Iterable<User> users = userController.findAll();
        model.addAttribute("users", users);

        return "user";
    }

    @GetMapping("/users/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("users", new User());
        return "add-user";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userController.delete(id);
        return "redirect:/admin/user";
    }

    @GetMapping("/users/block/{id}")
    public String blockUser(@PathVariable int id) {
        ResponseEntity<User> responseEntity = userController.block(id);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {

        } else {

        }
        return "redirect:/admin/user";
    }

    @GetMapping("/users/unblock/{id}")
    public String unblockUser(@PathVariable int id) {
        ResponseEntity<User> responseEntity = userController.unblock(id);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {

        } else {

        }
        return "redirect:/admin/user";
    }

}


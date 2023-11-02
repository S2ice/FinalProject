package com.example.finalproject.controllers;

import com.example.finalproject.models.Role;
import com.example.finalproject.models.User;
import com.example.finalproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class ApiUsers {
    UserRepository userRepository;

    @Autowired
    public ApiUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // POST /api/users/register
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        user.setRegistration_date();
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPhoto("https://wallpapers.com/images/hd/weird-profile-pictures-k7dzvlzmlq8q6eib.jpg");

        User newUser = userRepository.save(user);
        return ResponseEntity.ok(newUser);
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> findOne(@PathVariable(value = "id") int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /users
    @GetMapping("")
    public Iterable<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }


    // POST /users
    @PostMapping("")
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        user.setRegistration_date(); // Set the registration date
        User newUser = userRepository.save(user);
        return ResponseEntity.ok(newUser);
    }

    // PUT /users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable(value = "id") int id, @Valid @RequestBody User user) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User oldUser = userOptional.get();
        oldUser.setUsername(user.getUsername());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setName(user.getName());
        oldUser.setLastname(user.getLastname());
        oldUser.setAddress(user.getAddress());
        oldUser.setActive(user.isActive());
        oldUser.setRoles(user.getRoles());
        oldUser.setDescription(user.getDescription());
        oldUser.setPhoto(user.getPhoto());

        User updatedUser = userRepository.save(oldUser);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<User> block(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOptional.get();
        user.setActive(false);
        User blockedUser = userRepository.save(user);

        return ResponseEntity.ok(blockedUser);
    }

    @PutMapping("/unblock/{id}")
    public ResponseEntity<User> unblock(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOptional.get();
        user.setActive(true);
        User blockedUser = userRepository.save(user);

        return ResponseEntity.ok(blockedUser);
    }

    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable(value = "id") int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}

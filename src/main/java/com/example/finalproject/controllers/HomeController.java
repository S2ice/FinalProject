package com.example.finalproject.controllers;

import com.example.finalproject.models.Post;
import com.example.finalproject.models.User;
import com.example.finalproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    private final UserRepository userRepository;
    ApiPosts apiPosts;

    @Autowired
    public HomeController(UserRepository userRepository, ApiPosts apiPosts) {
        this.userRepository = userRepository;
        this.apiPosts = apiPosts;
    }

    @GetMapping("/")
    public String homePage() {
        return "login";
    }

    @GetMapping("/home/{username}")
    public String home(@PathVariable String username, Model model, Principal principal) {
        if (principal != null) {
            String currentUsername = principal.getName();

            if (currentUsername.equals(username)) {
                User user = userRepository.findByEmail(username);
                model.addAttribute("user", user);
                    List<Post> userPosts = apiPosts.getPostsByUser(user.getUserId());
                    model.addAttribute("posts", userPosts);
                    return "index";
                }
        }
        return "redirect:/";
    }

    @PostMapping("/createPost")
    public String createPost(@RequestParam String title, @RequestParam String content, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal != null) {
            String username = principal.getName();

            User user = userRepository.findByEmail(username);

            if (user != null) {
                int userId = user.getUserId();

                Post createdPost = apiPosts.createPost(userId, title, content, principal);

                if (createdPost != null) {
                    redirectAttributes.addFlashAttribute("successMessage", "Пост был успешно создан");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Не удалось создать пост");
                }
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Пользователь не найден");
            }
        }
        return "redirect:/home/" + principal.getName();
    }

    @GetMapping("/deletePost/{postId}")
    public String deletePost(@PathVariable int postId, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal != null) {
            User user = userRepository.findByEmail(principal.getName());

            if (user != null) {
                Optional<Post> post = apiPosts.postRepository.findById(postId);

                if (post.isPresent() && post.get().getUser().getUserId() == user.getUserId()) {
                    apiPosts.deletePost(postId); // Используем метод из ApiPosts для удаления поста
                    redirectAttributes.addFlashAttribute("successMessage", "Пост был успешно удален");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Пост не существует или не принадлежит текущему пользователю");
                }
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Пользователь не найден");
            }
        }

        return "redirect:/home/" + principal.getName();
    }

}

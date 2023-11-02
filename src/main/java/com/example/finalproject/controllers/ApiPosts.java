package com.example.finalproject.controllers;

import com.example.finalproject.models.Post;
import com.example.finalproject.models.User;
import com.example.finalproject.repositories.PostRepository;
import com.example.finalproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class ApiPosts {

    PostRepository postRepository;
    UserRepository userRepository;

    @Autowired
    public ApiPosts(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUser(@PathVariable int userId) {
        User user = new User();
        user.setUserId(userId);
        return postRepository.findByUser(user);
    }

    @PutMapping("/update/{postId}")
    public Post updatePost(@PathVariable int postId, @RequestBody Post updatedPost) {
        Post existingPost = postRepository.findById(postId).orElse(null);
        if (existingPost != null) {
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setContent(updatedPost.getContent());
            // Вы можете обновить другие поля если это необходимо.
            return postRepository.save(existingPost);
        }
        return null;
    }

    @DeleteMapping("/delete/{postId}")
    public void deletePost(@PathVariable int postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            postRepository.delete(post.get());
        }
    }

    @PostMapping("/create")
    public Post createPost(@RequestParam int userId, @RequestParam String title, @RequestParam String content, Principal principal) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            Post post = new Post();
            post.setUser(user);
            post.setTitle(title);
            post.setContent(content);
            post.setCreation_date();

            return postRepository.save(post);
        } else {
            return null;
        }
    }
}

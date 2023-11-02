package com.example.finalproject.repositories;

import com.example.finalproject.models.Post;
import com.example.finalproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
}

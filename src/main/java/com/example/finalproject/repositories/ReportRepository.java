package com.example.finalproject.repositories;

import com.example.finalproject.models.Post;
import com.example.finalproject.models.Report;
import com.example.finalproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer>{
    List<Report> findByUser(User user);
}

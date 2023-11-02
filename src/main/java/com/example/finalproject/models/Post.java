package com.example.finalproject.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @NotBlank(message = "title is required")
    @Column(name = "title")
    private String title;
    @NotBlank(message = "content is required")
    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private String creation_date;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.creation_date = dateFormat.format(new Date());
    }
}

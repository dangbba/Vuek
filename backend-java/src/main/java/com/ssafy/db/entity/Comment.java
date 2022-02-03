package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private int id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String comment_content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @PrePersist
    public void created_at() {
        this.created_at = LocalDateTime.now();
    }
}

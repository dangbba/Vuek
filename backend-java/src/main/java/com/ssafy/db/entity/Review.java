package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_detail_id")
    private BookDetail bookDetail;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String review_content;
    private String review_name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @PrePersist
    public void created_at() {
        this.created_at = LocalDateTime.now();
    }

    public void updated_at() {
        this.updated_at = LocalDateTime.now();
    }

}
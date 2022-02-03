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
    private int id;
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

}
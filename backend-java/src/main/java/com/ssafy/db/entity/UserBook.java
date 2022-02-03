package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class UserBook {

    @Id
    @GeneratedValue
    @Column(name = "user_book_id")
    private int id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_detail_id")
    private BookDetail bookDetail;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}

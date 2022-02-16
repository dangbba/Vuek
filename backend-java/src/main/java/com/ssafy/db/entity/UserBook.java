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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userBookId")
    private int id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bookDetailId", unique = true)
    private BookDetail bookDetail;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;
}

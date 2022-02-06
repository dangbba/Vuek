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
    @Column(name = "userBook_Id")
    private int id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bookDetailId")
    private BookDetail bookDetail;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;
}

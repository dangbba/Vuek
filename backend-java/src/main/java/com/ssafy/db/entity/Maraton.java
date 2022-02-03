package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Maraton {
    @Id
    @GeneratedValue
    @Column(name = "maraton_id")
    private int id;
    @JoinColumn(name = "user_id")
    private String user_user_id;
    private int goal_pages;
    private int now_pages;
}

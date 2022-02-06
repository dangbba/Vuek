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
    @Column(name = "maratonId")
    private int id;
    @JoinColumn(name = "userId")
    private String userId;
    private int goalPages;
    private int nowPages;
}

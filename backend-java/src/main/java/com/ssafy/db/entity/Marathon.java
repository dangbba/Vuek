package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Marathon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marathonId")
    private int id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;
    private int goalPages;
    private int nowPages;

}

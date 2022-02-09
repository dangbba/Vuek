package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Marathon {
    @Id
    @GeneratedValue
    @Column(name = "marathonId")
    private int id;
    @JoinColumn(name = "userId")
    private String userId;
    private int goalPages = 50;
    private int nowPages;

    public void nowPages() {
        this.nowPages++;
    }
}

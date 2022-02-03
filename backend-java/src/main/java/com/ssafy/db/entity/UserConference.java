package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class UserConference {

    @Id
    @GeneratedValue
    @Column(name = "user_conference_id")
    private int id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_Id")
    private User user;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "conference_id")
    private Conference conference;
}

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userConferenceId")
    private int id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "conferenceId")
    private Conference conference;

}

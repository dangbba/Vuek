package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class ConferenceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conferenceHistoryId")
    private long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "conferenceId")
    private Conference conference;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;
    private int action;
    private LocalDateTime insertedTime;

    @PrePersist
    public void insertedTime() {
        this.insertedTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}

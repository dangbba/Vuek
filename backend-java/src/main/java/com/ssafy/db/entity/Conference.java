package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Conference {

    @Id
    @GeneratedValue
    @Column(name = "conferenceId")
    private long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bookDetailId")
    private BookDetail bookDetail;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "conferenceTypeId")
    private ConferenceType conferenceType;
    private LocalDateTime callStartTime;
    private LocalDateTime callEndTime;
    private String thumbnailUrl;
    private String title;
    private int isActive;
    private String participant;
    private String description;

    @PrePersist
    public void callStartTime() {
        this.callStartTime = LocalDateTime.now();
    }

    public void callEndTime() {
        this.callEndTime = LocalDateTime.now();
    }

}

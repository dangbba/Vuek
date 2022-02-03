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
    @Column(name = "conference_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_detail_id")
    private BookDetail bookDetail;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "conference_type_id")
    private ConferenceType conferenceType;
    private LocalDateTime callStartTime;
    private LocalDateTime callEndTime;
    private String thumbnail_url;
    private String title;
    private int is_active;
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

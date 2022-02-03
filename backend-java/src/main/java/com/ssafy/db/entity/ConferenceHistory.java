package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class ConferenceHistory {
    @Id
    @GeneratedValue
    @Column(name = "conference_history_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "conference_id")
    private Conference conference;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private int action;
    private LocalDateTime inserted_time;

    @PrePersist
    public void inserted_time() {
        this.inserted_time = LocalDateTime.now();
    }
}

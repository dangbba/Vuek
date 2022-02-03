package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class ConferenceBookmark {
    @Id
    @GeneratedValue
    @Column(name = "conference_bookmark_id")
    private int id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "conference_id")
    private Conference conference;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_detail_id")
    private BookDetail bookDetail;
    private String content;
}

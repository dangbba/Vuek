package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String callStartTime;
    private LocalDateTime callEndTime;
    private String thumbnailUrl;
    private String title;
    private int isActive;
//    @ElementCollection(targetClass=User.class)
    @OneToMany(targetEntity=UserConference.class, mappedBy="user", fetch=FetchType.EAGER)
    private List<User> participant;
    private String description;

    @PrePersist
    public void callStartTime() {
        String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        this.callStartTime = formatDate;
    }

    public void callEndTime() {
        this.callEndTime = LocalDateTime.now();
    }

}

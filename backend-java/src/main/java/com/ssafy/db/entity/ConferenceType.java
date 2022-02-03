package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ConferenceType {
    @Id
    @GeneratedValue
    @Column(name = "conference_type_id")
    private int id;
    private String name;
}

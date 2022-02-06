package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class BookDetail {
    @Id
    @GeneratedValue
    @Column(name = "bookDetailId")
    private int id;
    private String author;
    private String publisher;
    private String titleUrl;
    private String overview;
    private int price;
    private String title;
    private String publishDate;
    private String sailStatus;
}

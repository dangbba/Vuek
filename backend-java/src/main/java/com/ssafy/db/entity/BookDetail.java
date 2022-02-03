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
    @Column(name = "book_detail_id")
    private int id;
    private String author;
    private String publisher;
    private String title_url;
    private String overview;
    private int price;
    private String title;
    private String publish_date;
    private String sail_status;
}

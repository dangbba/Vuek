package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class BookDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookDetailId")
    private int id;

    private String isbn;
    private String author;
    private String publisher;
    private String titleUrl;
    private String overview;
    private int price;
    private String title;
    private String publishDate;
    private String sailStatus;
}

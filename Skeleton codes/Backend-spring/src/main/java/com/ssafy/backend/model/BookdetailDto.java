package com.ssafy.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookdetailDto {
	private int id;
	private String author;
	private String publisher;
	private String title_url;
	private String overview;
	private int price;
	private String title;
	private String publish_date;
	
}

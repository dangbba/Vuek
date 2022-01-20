package com.ssafy.backend.model;

import lombok.Data;

@Data
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

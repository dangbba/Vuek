package com.ssafy.backend.model;

import lombok.Data;

@Data
public class ReviewDto {
	
	private int id;
	private int book_detail_id;
	private String user_user_id;
	private String review_content;
	private String review_name;
	private String created_at;
	private String updated_at;
}

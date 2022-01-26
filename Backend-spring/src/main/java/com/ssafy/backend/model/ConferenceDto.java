package com.ssafy.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConferenceDto {
	private int id;
	private int user_user_id;
	private int book_detail_id;
	private int conference_type;
	private String call_start_time;
	private String call_end_time;
	private String thumbnail_url;
	private String title;
	private int is_active;
	
	private int empty;
	private int first;
	private int last;
	private int number;
	private int numberOfElements;
	
	private int offset;
	private int pageNumber;
	private int pageSize;
	private int paged;
	private int unpaged;
	
	private int size;
	private int totalElements;
	private int totalPages;
}

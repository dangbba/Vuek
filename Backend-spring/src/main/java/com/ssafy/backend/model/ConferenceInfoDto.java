package com.ssafy.backend.model;

import lombok.Data;

@Data
public class ConferenceInfoDto {
	private int id;
	private int user_user_id;
	private int book_detail_id;
	private int conference_type_name;
	private String call_start_time;
	private String thumbnail_url;
	private String title;
	private int is_active;
	
	private int participant;
}

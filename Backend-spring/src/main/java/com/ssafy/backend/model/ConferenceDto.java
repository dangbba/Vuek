package com.ssafy.backend.model;

import lombok.Data;

@Data
public class ConferenceDto {
	private int idconference;
	private int conference_type_idconference_type;
	private int book_detail_idbook_detail;
	private int user_iduser;
	private String call_start_time;
	private String call_end_time;
	private String thumbnail_url;
	private String conference_title;
	private int is_active;
}

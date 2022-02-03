package com.ssafy.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConferenceHistoryDto {
	private int id;
	private int conference_id;
	private String user_user_id;
	private int action;
	private String inserted_time;
}

package com.ssafy.backend.model;

import lombok.Data;

@Data
public class MessageDto {
	private String Conferenceid;
	private String user_name;
    private String content;
    
	public String getWriter() {
		return user_name;
	}
	public String getMessage(String string) {
		return string;
		
	}
	public String getConferenceId() {
		return Conferenceid;
	}
}
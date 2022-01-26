package com.ssafy.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDto {
	
	private int id;
	private int review_id;
	private String user_user_id;
	private String comment_content;
	private String created_at;
	private String updated_at;
}

package com.ssafy.backend.model;

import lombok.Data;

@Data
public class UserDto {
	private int iduser;
	private int rate_idrate;
	private String user_name;
	private String user_id;
	private String password;
}

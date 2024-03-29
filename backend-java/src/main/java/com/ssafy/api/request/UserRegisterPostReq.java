package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserRegisterPostRequest")
public class UserRegisterPostReq {
	@ApiModelProperty(name="유저 ID", example="ssafy_web")
	String userId;
	@ApiModelProperty(name="유저 Password", example="your_password")
	String password;
	@ApiModelProperty(name="유저 Name", example="your_name")
	String userName;
	@ApiModelProperty(name="유저 email", example="email")
	String email;
	@ApiModelProperty(name="유저 genre", example="genre")
	String genre;
	@ApiModelProperty(name="유저 goal", example="goal")
	String goal;
	@ApiModelProperty(name="socialLink", example="socialLink")
	String socialLink;
	@ApiModelProperty(name="유저 progileImage", example="email")
	String profileImage;




}

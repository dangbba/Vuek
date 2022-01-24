package com.ssafy.backend.model.service;

import java.util.List;

import com.ssafy.backend.model.UserDto;

public interface UserService {
	boolean idCheck(String id) throws Exception;
	boolean registerUser(UserDto userDto) throws Exception;
	UserDto login(String id) throws Exception;
	UserDto getUser(String id) throws Exception;
	UserDto getMyInfo() throws Exception;
	boolean updateUser(UserDto userDto) throws Exception;
	boolean deleteUser(String id) throws Exception;
	
	List<UserDto> listUser() throws Exception;
}

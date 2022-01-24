package com.ssafy.backend.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.backend.model.UserDto;

@Mapper
public interface UserMapper {
	
	int idCheck(String id) throws SQLException;
	boolean registerUser(UserDto userDto) throws SQLException;
	UserDto login(String id) throws SQLException;
	UserDto getUser(String id) throws SQLException;
	UserDto getMyInfo() throws SQLException;
	boolean updateUser(UserDto userDto) throws SQLException;
	boolean deleteUser(String id) throws SQLException;
	List<UserDto> listUser() throws SQLException;
	
}

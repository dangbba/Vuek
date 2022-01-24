package com.ssafy.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.backend.model.UserDto;
import com.ssafy.backend.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public boolean idCheck(String id) throws Exception {
		int cnt = userMapper.idCheck(id);
		return cnt == 1 ? true : false;
	}

	@Override
	public boolean registerUser(UserDto userDto) throws Exception {
		return userMapper.registerUser(userDto);
	}

	@Override
	public UserDto login(String id) throws Exception {
		return userMapper.login(id);
	}

	@Override
	public UserDto getUser(String id) throws Exception {
		return userMapper.getUser(id);
	}

	@Override
	public boolean updateUser(UserDto userDto) throws Exception {
		return userMapper.updateUser(userDto);
	}

	@Override
	public boolean deleteUser(String id) throws Exception {
		return userMapper.deleteUser(id);
	}

	@Override
	public List<UserDto> listUser() throws Exception {
		return userMapper.listUser();
	}

	@Override
	public UserDto getMyInfo() throws Exception {
		return userMapper.getMyInfo();
	}

}

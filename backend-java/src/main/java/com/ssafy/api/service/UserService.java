package com.ssafy.api.service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdateReq;
import com.ssafy.db.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserId(String user_id);
	boolean idCheck(String user_id);
	void deleteUser(String user_id);
	void updateUser(User user);
//	void updateUser(UserUpdateReq userUpdateReq);

}

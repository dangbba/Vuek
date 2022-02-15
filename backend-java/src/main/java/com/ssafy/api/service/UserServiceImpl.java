package com.ssafy.api.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.request.UserUpdateReq;
import com.ssafy.db.entity.QUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import org.springframework.web.multipart.MultipartFile;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	@Autowired
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		user.setUserId(userRegisterInfo.getUserId());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		user.setUserName(userRegisterInfo.getUserName());
		user.setEmail(userRegisterInfo.getEmail());
		user.setGenre(userRegisterInfo.getGenre());
		user.setGoal(userRegisterInfo.getGoal());
		user.setSocialLink(userRegisterInfo.getSocialLink());
		user.setProfileImage(userRegisterInfo.getProfileImage());
		return userRepository.save(user);
	}
	@Override
	public User getUserByUserId(String user_id) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByUserId(user_id).get();
		return user;
	}
	@Override
	public boolean idCheck(String user_id) {
		QUser qUser = QUser.user;
		User usercheck = jpaQueryFactory.select(qUser).from(qUser)
				.where(qUser.userId.eq(user_id)).fetchOne();
		if (usercheck == null) {return false;}
		return true;
	}

	@Override
	public void deleteUser(String user_id){
//		userRepositorySupport.deleteUserByUserId(user);
		userRepositorySupport.deleteByUserId(user_id);
	}

	@Override
	public void updateUser(User user){
		userRepositorySupport.updateUser(user);
	}

//	@Override
//	public void updateUser(UserUpdateReq userUpdateReq) {
//		try {
//			userRepositorySupport.updateUser(userUpdateReq);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}

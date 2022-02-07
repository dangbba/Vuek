package com.ssafy.api.controller;

import com.ssafy.api.request.UserLoginPostReq;
import com.ssafy.api.service.JwtService;
import com.ssafy.api.service.UserService;
import com.ssafy.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "인증 API", tags = {"Auth."})
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";

	@Autowired
	UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	public ResponseEntity<Map<String, Object>> login(@RequestBody @ApiParam(value = "로그인 정보", required = true) UserLoginPostReq loginInfo) {
		String user_id = loginInfo.getUserId();
		String password = loginInfo.getPassword();
		logger.debug("login - 호출");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			if (user_id.equals(userService.getUserByUserId(user_id).getUserId())) {
				User user = userService.getUserByUserId(user_id);
				if (passwordEncoder.matches(password, user.getPassword())) {
					System.out.println("55!!!!!!!!!!!!!!!!!!!!!!!!!!");
					String token = jwtService.create("userId", user.getUserId(), "accessToken");// key, data, subject
					System.out.println("57!!!!!!!!!!!!!!!!!!!!!!!!!!");
					logger.debug("로그인 토큰정보 : {}", token);
					resultMap.put("accessToken", token);
					resultMap.put("message", SUCCESS);
					status = HttpStatus.ACCEPTED;
				} else {
					resultMap.put("login failed", "잘못된 비밀번호입니다.");
					status = HttpStatus.UNAUTHORIZED;
				}
			} else {
				resultMap.put("message", "존재하지 않는 계정입니다.");
				status = HttpStatus.NOT_FOUND;
			}
		} catch (Exception error) {
			resultMap.put("message", error.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}

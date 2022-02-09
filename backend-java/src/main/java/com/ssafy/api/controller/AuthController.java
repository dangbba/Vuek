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
import org.springframework.web.bind.annotation.*;

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
	private static final String FAIL = "fail";

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
			if (userService.idCheck(user_id)) {
				User user = userService.getUserByUserId(user_id);
				if (passwordEncoder.matches(password, user.getPassword())) {
					String token = jwtService.create("userId", user.getUserId(), "access-token");// key, data, subject
					logger.debug("로그인 토큰정보 : {}", token);
					resultMap.put("access-token", token);
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

	@ApiOperation(value = "회원가입시 아이디 중복 확인한다. 사용 가능 여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping("/idcheck")
	public ResponseEntity<String> idCheck(@RequestParam("id") String checkId) throws Exception{
		logger.debug("idCheck - 호출");
		if(userService.idCheck(checkId)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}

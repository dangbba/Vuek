package com.ssafy.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.model.LoginDto;
import com.ssafy.backend.model.UserDto;
import com.ssafy.backend.model.service.JwtService;
import com.ssafy.backend.model.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ALREADYEXISTS = "이미 존재하는 사용자 ID입니다.";
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	//중복확인 완료
	@ApiOperation(value = "회원가입시 아이디 중복 확인한다. 사용 가능 여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping("/{user_id}")
	public ResponseEntity<String> idCheck(@RequestParam("user_id") String checkId) throws Exception{
		logger.debug("idCheck - 호출");
		if(userService.idCheck(checkId)) {
			return new ResponseEntity<String>(ALREADYEXISTS, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	//회원가입 완료
	@ApiOperation(value = "회원가입을 한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> register(@RequestBody UserDto userDto) throws Exception{
		logger.debug("idCheck - 호출");
		
		if(!userService.idCheck(userDto.getUser_id())) {
			if(userService.registerUser(userDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		}
		else {
			return new ResponseEntity<String>(ALREADYEXISTS, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	//로그인 완료
	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/auth/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) LoginDto loginDto) {
		logger.debug("login - 호출");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			if (userService.idCheck(loginDto.getUser_id())) {
				UserDto loginUser = userService.getUser(loginDto.getUser_id());
				if (loginDto.getPassword().equals(loginUser.getPassword())) {
					
					String token = jwtService.create("user_id", loginUser.getUser_id(), "access-token");// key, data, subject
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
		} catch(Exception error) {
			logger.error("로그인 실패 : {}", error);
			resultMap.put("message", error.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;			
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{user_id}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("user_id") @ApiParam(value = "인증할 회원의 아이디.", required = true) String user_id,
			HttpServletRequest request) {
		logger.debug("getInfo - 호출");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		System.out.println(request.getHeader("access-token"));
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				UserDto userDto = userService.getUser(user_id);
				LoginDto loginDto = new LoginDto();
				loginDto.setUser_id(user_id);
				loginDto.setPassword(userDto.getPassword());
				
				resultMap.put("userInfo", userDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원수정 한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/{user_id}")
	public ResponseEntity<String> update(@RequestBody UserDto userDto) throws Exception{
		logger.debug("update - 호출");
		if(userService.updateUser(userDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "회원탈퇴 한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{user_id}")
	public ResponseEntity<String> delete(@PathVariable String user_id) throws Exception{
		logger.debug("delete - 호출");
		if(userService.deleteUser(user_id)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "모든 회원을 조회 한다. 그리고 DB조회 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping
	public ResponseEntity<List<UserDto>> list() throws Exception {
		logger.debug("list - 호출");
		return new ResponseEntity<>(userService.listUser(), HttpStatus.OK);
	}
}
package com.ssafy.api.controller;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdateReq;
import com.ssafy.api.service.JwtService;
import com.ssafy.api.service.UserBookService;
import com.ssafy.api.service.UserService;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.User;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User."})
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ALREADYEXISTS = "이미 존재하는 사용자 ID입니다.";

	@Autowired
	UserService userService;

	@Autowired
	private JwtService jwtService;
	
	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<String> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {
		String user_id = registerInfo.getUserId();
		try{
			if (!userService.idCheck(user_id)) {
				User user = userService.createUser(registerInfo);
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(ALREADYEXISTS, HttpStatus.CONFLICT);
			}
		}catch (Exception error) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/me/{userId}")
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	public ResponseEntity<Map<String, Object>> getUserInfo(
			@PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
//		System.out.println(request.getHeader("access-token"));

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
				User user = userService.getUserByUserId(userId);
				resultMap.put("userInfo", user);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}	catch (Exception e) {
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

//		return ResponseEntity.status(200).body(UserRes.of(user));
	}

	@ApiOperation(value = "유저를 삭제한다, SUCCESS/FAIL", response = String.class)
	@DeleteMapping("/{user_id}")
	public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable String user_id) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			System.out.println(user_id + " : user Id test!!!!!!!!!!!");
			userService.deleteUser(user_id);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception error) {
			resultMap.put("message", error.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "유저정보를 수정한다, SUCCESS/FAIL", response = String.class)
	@PutMapping("/{user_id}")
	public ResponseEntity<String> updateUser(@RequestBody User user) throws Exception {
		userService.updateUser(user);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

//	@ApiOperation(value = "유저정보를 수정한다, SUCCESS/FAIL", response = String.class)
//	@PutMapping("/{user_id}")
//	public ResponseEntity<String> updateUser(@RequestBody UserUpdateReq userUpdateReq) throws Exception {
//		System.out.println("-----------------test-------------------");
//		System.out.println(userUpdateReq.getUser());
//		System.out.println(userUpdateReq.getUser().getProfileImage());
//		System.out.println("-----------------test-------------------");
////		userService.updateUser(userUpdateReq);
//		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//	}

}

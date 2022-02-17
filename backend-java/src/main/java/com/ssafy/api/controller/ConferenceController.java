package com.ssafy.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ssafy.api.request.ConferenceJoinPostReq;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.db.dto.EnterWrapperDto;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceHistory;
import com.ssafy.db.entity.ConferenceType;
import com.ssafy.db.entity.User;
import io.swagger.models.auth.In;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpSession;

import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/v1/conferences")
public class ConferenceController {
	public static final Logger logger = LoggerFactory.getLogger(ConferenceController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	private OpenVidu openVidu;
	private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
	private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();

	private String OPENVIDU_URL;
	private String SECRET;

	public ConferenceController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
		this.SECRET = secret;
		this.OPENVIDU_URL = openviduUrl;
		this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
	}
	@Autowired
	private ConferenceService conferenceService;

	@ApiOperation(value = "방을 생성한다. 그리고 DB저장 여부에 따라 성공/실패를 반환한다.", response = String.class)
	@PostMapping("/create")
	public ResponseEntity<Long> createConference(@RequestBody Conference conference) throws Exception {
		conferenceService.createConference(conference);
		Long conferenceId = conference.getId();
		return new ResponseEntity<Long>(conferenceId, HttpStatus.OK);
	}

	@ApiOperation(value = "방을 종료한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/close")
	public ResponseEntity<String> closeConference(@RequestParam Integer idconference) throws Exception {
		System.out.println(idconference);
		Conference conference = conferenceService.getConferenceInfo(idconference);
		if (conference != null) {
			try {
				conferenceService.closeConference(conference);
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(FAIL, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);

	}

	// 방 삭제
	@ApiOperation(value = "방을 DB에서 삭제한다", response = String.class)
	@PostMapping("/delete")
	public ResponseEntity<String> deleteConference(@RequestParam Integer idconference) throws Exception {
		Conference conference = conferenceService.getConferenceInfo(idconference);
		if (conferenceService.deleteConference(conference)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "방 카테고리를 조회한다.", response = ConferenceType.class)
	@GetMapping("/conference-categories")
	public ResponseEntity<List<ConferenceType>> getConferenceCategory() throws Exception {
		return new ResponseEntity<>(conferenceService.getConferenceCategory(), HttpStatus.OK);
		
	}
	@ApiOperation(value = "방 상세정보를 조회한다.", response = Conference.class)
	@GetMapping("/conference-info/{idconference}")
	public ResponseEntity<Conference> getConferenceInfo(@PathVariable int idconference) throws Exception {
		return new ResponseEntity<>(conferenceService.getConferenceInfo(idconference), HttpStatus.OK);
	}
	
	@ApiOperation(value = "conferenceId에 해당하는 글의 내용을 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/conference-info")
	public ResponseEntity<String> updateConferenceInfo(@RequestBody Conference conference) throws Exception {
		logger.debug("updateConferenceInfo - 호출");
		conferenceService.updateConferenceInfo(conference);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);

	}
	
	@ApiOperation(value = "방 목록을 조회한다.", response = Conference.class)
	@GetMapping
	public ResponseEntity<List<Conference>> getConference() throws Exception {
		return new ResponseEntity<>(conferenceService.getConference(), HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "방 목록을 방생성시간, 타이틀로  정렬한다.", response = Conference.class)
	@GetMapping("/getConferenceBySort")
	public ResponseEntity<List<Conference>> getConferenceBySort(@RequestParam String sort, @RequestParam String asc) throws Exception {
		return new ResponseEntity<>(conferenceService.getConferenceBySort(sort, asc), HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "방 목록을 타이틀로 검색한다.", response = Conference.class)
	@GetMapping("/searchByTitle")
	public ResponseEntity<List<Conference>> searchByTitle(@RequestParam String word) throws Exception {
		return new ResponseEntity<>(conferenceService.searchByTitle(word), HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "방 목록을 카테고리로 필터링한다.", response = Conference.class)
	@GetMapping("/getConferenceByCategory")
	public ResponseEntity<List<Conference>> getConferenceByCategory(@RequestParam int conference_type_id) throws Exception {
		return new ResponseEntity<>(conferenceService.getConferenceByCategory(conference_type_id), HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "지난 회의 이력을 조회한다.", response = Conference.class)
	@GetMapping("/getConferenceHistory")
	public ResponseEntity<List<ConferenceHistory>> getConferenceHistory(@RequestParam String user_id) throws Exception {
		return new ResponseEntity<>(conferenceService.getConferenceHistory(user_id), HttpStatus.OK);
		
	}

	@ApiOperation(value = "이력을 생성한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/createHistory")
	public ResponseEntity<String> createConferenceHistory( @RequestBody ConferenceHistory conferenceHistory ) throws Exception {
		conferenceService.createConferenceHistory(conferenceHistory);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	private ResponseEntity<JSONObject> getErrorResponse(Exception e) {
		JSONObject json = new JSONObject();
		json.put("cause", e.getCause());
		json.put("error", e.getMessage());
		json.put("exception", e.getClass());
		return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ApiOperation(value = "방에 참여한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/join")
	public ResponseEntity<String> enterConference( @RequestBody EnterWrapperDto enterWrapperDto ) throws Exception {
		conferenceService.enterConference(enterWrapperDto);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);

	}

	@ApiOperation(value = "방 인원수를 센다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/count/{idconference}")
	public ResponseEntity<Integer> countNumOfPeople(@PathVariable int idconference ) throws Exception {
		long num = conferenceService.countNumOfPeople(idconference);
		return new ResponseEntity<Integer>((int) num, HttpStatus.OK);
	}

	@ApiOperation(value = "참여자가 방을 나간다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/participantClose/{idconference}/{user_id}")
	public ResponseEntity<String> participantClose(@PathVariable int idconference, @PathVariable String user_id ) throws Exception {
		conferenceService.participantClose(idconference, user_id);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	@ApiOperation(value = "방 참여자들을 조회한다.", response = Conference.class)
	@GetMapping("/getParticipants/{idconference}")
	public ResponseEntity<List<User>> getParticipants(@PathVariable int idconference) throws Exception {
		List<User> list = conferenceService.getParticipants(idconference);
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);

	}

	@ApiOperation(value = "infinite Loading")
	@GetMapping("/conferencesLimit")
	public Object getConferencesLimit(@RequestParam("limit") int limit) throws Exception {
		List<Conference> list = conferenceService.getConferencesLimit(limit);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}

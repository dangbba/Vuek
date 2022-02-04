package com.ssafy.api.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ssafy.api.service.ConferenceService;
import com.ssafy.db.dto.EnterWrapperDto;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceHistory;
import com.ssafy.db.entity.ConferenceType;
import io.openvidu.java.client.*;
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
	//////////
	@ApiOperation(value = "방을 생성한다. 그리고 JSON객체를 반환한다.", response = String.class)
	@PostMapping("/create")
	public ResponseEntity<String> createConference(@RequestBody Conference conference) throws Exception {
		conferenceService.createConference(conference);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	@ApiOperation(value = "존재하는 방에 참여하며 Json객체를 반환한다", response = JSONObject.class)
	@PostMapping("/join")
	public ResponseEntity<JSONObject> getToken(@RequestBody String sessionNameParam, HttpSession httpSession, EnterWrapperDto enterWrapperDto) throws Exception {
		System.out.println("Getting a token from OpenVidu Server | {sessionName}=" + sessionNameParam);
		JSONObject sessionJSON = (JSONObject) new JSONParser().parse(sessionNameParam);

		String sessionName = (String) sessionJSON.get("sessionId");
		String serverData = "{\"serverData\": \"" + httpSession.getAttribute("loggedUser") + "\"}";
		OpenViduRole role = OpenViduRole.SUBSCRIBER;
		if (httpSession.getAttribute("role") == "SUBSCRIBER") {
			role = OpenViduRole.PUBLISHER;
		}
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).data(serverData).role(role).build();

		JSONObject responseJson = new JSONObject();

		if (this.mapSessions.get(sessionName) != null) {
			System.out.println("Existing session" + sessionName);
			try {
				String token = this.mapSessions.get(sessionName).createConnection(connectionProperties).getToken();

				this.mapSessionNamesTokens.get(sessionName).put(token, role);
				responseJson.put(0, token);
				conferenceService.enterConference(enterWrapperDto);
				return new ResponseEntity<>(responseJson, HttpStatus.OK);
			} catch (OpenViduJavaClientException e1) {
				return getErrorResponse(e1);
			} catch (OpenViduHttpException e2) {
				if (404 == e2.getStatus()) {
					this.mapSessions.remove(sessionName);
					this.mapSessionNamesTokens.remove(sessionName);
				}
			}
		}
		return new ResponseEntity<>(responseJson, HttpStatus.CONFLICT);
	}

	@ApiOperation(value = "방에서 나간다.", response = HttpStatus.class)
	@PostMapping("/remove")
	public ResponseEntity<JSONObject> removeUser(@RequestBody String sessionNameToken, HttpSession httpSession) throws Exception {
		System.out.println("Removing user | {sessionName, token}=" + sessionNameToken);

		JSONObject sessionNameTokenJSON = (JSONObject) new JSONParser().parse(sessionNameToken);
		String sessionName = (String) sessionNameTokenJSON.get("sessionName");
		String token = (String) sessionNameTokenJSON.get("token");

		//세션 존재성 검사
		if (this.mapSessions.get(sessionName) != null && this.mapSessionNamesTokens.get(sessionName) != null) {
			if (this.mapSessionNamesTokens.get(sessionName).remove(token) != null) {
				if (this.mapSessionNamesTokens.get(sessionName).isEmpty()) {
					this.mapSessions.remove(sessionName);
				}
				//DB에서 지우는 과정 수행 필요
				return new ResponseEntity<>(HttpStatus.OK);

			} else {
				//토큰 유효성 에러
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private ResponseEntity<JSONObject> getErrorResponse(Exception e) {
		JSONObject json = new JSONObject();
		json.put("cause", e.getCause());
		json.put("error", e.getMessage());
		json.put("exception", e.getClass());
		return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/////// is_active 수정
	@ApiOperation(value = "방을 종료한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/close/{idconference}")
	public ResponseEntity<String> closeConference(@RequestBody Conference conference) throws Exception {
		conferenceService.closeConference(conference);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
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
	
	@ApiOperation(value = "idconference에 해당하는 글의 내용을 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/conference-info/{idconference}")
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
	
}

package com.ssafy.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.model.ConferenceHistoryDto;
import com.ssafy.backend.model.EnterWrapperDto;
import com.ssafy.backend.model.service.ConferenceService;

import io.swagger.annotations.ApiOperation;

@Transactional
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/enter")
public class EnterConferenceController {
	
	public static final Logger logger = LoggerFactory.getLogger(EnterConferenceController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private ConferenceService conferenceService;
	
	@ApiOperation(value = "방에 참여한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/createParticipant")
	public ResponseEntity<String> enterConference( @RequestBody EnterWrapperDto enterWrapperDto ) throws Exception {
		if(conferenceService.enterConference(enterWrapperDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "방에 참여한다. 이력을 생성한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/createHistory")
	public ResponseEntity<String> createConferenceHistory( @RequestBody ConferenceHistoryDto conferenceHistoryDto ) throws Exception {
		if(conferenceService.createConferenceHistory(conferenceHistoryDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}

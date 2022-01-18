package com.ssafy.backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.model.ConferenceDto;
import com.ssafy.backend.model.ConferenceTypeDto;
import com.ssafy.backend.model.service.ConferenceService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/conferences")
public class ConferenceController {
	
	public static final Logger logger = LoggerFactory.getLogger(ConferenceController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private ConferenceService conferenceService;
	
	@ApiOperation(value = "방을 생성한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> createConference(@RequestBody ConferenceDto conferenceDto) throws Exception {
		if(conferenceService.createConference(conferenceDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "방 카테고리를 조회한다.", response = ConferenceTypeDto.class)
	@GetMapping("/conference-categories")
	public ResponseEntity<List<ConferenceTypeDto>> getConferenceCategory() throws Exception {
		return new ResponseEntity<>(conferenceService.getConferenceCategory(), HttpStatus.OK);
		
	}
	
//	@ApiOperation(value = "방 상세정보를 조회한다.", response = ConferenceDto.class)
//	@GetMapping("/conference-info")
//	public ResponseEntity<ConferenceDto> getConference throws Exception {
//		return new ResponseEntity<>(conferenceService.getConferenceCategory(), HttpStatus.OK);
//		
//	}
}

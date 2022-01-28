package com.ssafy.backend.controller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.ssafy.backend.model.ConferenceDto;
import com.ssafy.backend.model.ConferenceHistoryDto;
import com.ssafy.backend.model.ConferenceInfoDto;
import com.ssafy.backend.model.ConferenceTypeDto;
import com.ssafy.backend.model.EnterWrapperDto;
import com.ssafy.backend.model.UserDto;
import com.ssafy.backend.model.service.ConferenceService;
import com.ssafy.backend.model.service.UserService;

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
	
	@ApiOperation(value = "방을 종료한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/close/{idconference}")
	public ResponseEntity<String> closeConference(@RequestBody ConferenceDto conferenceDto) throws Exception {
		if(conferenceService.closeConference(conferenceDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	
	@ApiOperation(value = "방 카테고리를 조회한다.", response = ConferenceTypeDto.class)
	@GetMapping("/conference-categories")
	public ResponseEntity<List<ConferenceTypeDto>> getConferenceCategory() throws Exception {
		return new ResponseEntity<>(conferenceService.getConferenceCategory(), HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "방 상세정보를 조회한다.", response = ConferenceDto.class)
	@GetMapping("/conference-info/{idconference}")
	public ResponseEntity<ConferenceInfoDto> getConferenceInfo(@PathVariable int idconference) throws Exception {
		return new ResponseEntity<>(conferenceService.getConferenceInfo(idconference), HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "idconference에 해당하는 글의 내용을 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/conference-info/{idconference}")
	public ResponseEntity<String> updateConferenceInfo(@RequestBody ConferenceInfoDto conferenceInfoDto) throws Exception {
		logger.debug("updateConferenceInfo - 호출");
		if(conferenceService.updateConferenceInfo(conferenceInfoDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "방 목록을 조회한다.", response = ConferenceDto.class)
	@GetMapping
	public ResponseEntity<List<ConferenceDto>> getConference() throws Exception {
		return new ResponseEntity<>(conferenceService.getConference(), HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "방 목록을 방생성시간, 타이틀로  정렬한다.", response = ConferenceDto.class)
	@GetMapping("/getConferenceBySort")
	public ResponseEntity<List<ConferenceDto>> getConferenceBySort(@RequestParam String sort, @RequestParam String asc) throws Exception {
		return new ResponseEntity<>(conferenceService.getConferenceBySort(sort, asc), HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "방 목록을 타이틀로 검색한다.", response = ConferenceDto.class)
	@GetMapping("/searchByTitle")
	public ResponseEntity<List<ConferenceDto>> searchByTitle(@RequestParam String word) throws Exception {
		return new ResponseEntity<>(conferenceService.searchByTitle(word), HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "방 목록을 카테고리로 필터링한다.", response = ConferenceDto.class)
	@GetMapping("/getConferenceByCategory")
	public ResponseEntity<List<ConferenceDto>> getConferenceByCategory(@RequestParam String categoryType) throws Exception {
		return new ResponseEntity<>(conferenceService.getConferenceByCategory(categoryType), HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "지난 회의 이력을 조회한다.", response = ConferenceDto.class)
	@GetMapping("/getConferenceHistory")
	public ResponseEntity<List<ConferenceHistoryDto>> getConferenceHistory(@RequestParam String user_id) throws Exception {
		return new ResponseEntity<>(conferenceService.getConferenceHistory(user_id), HttpStatus.OK);
		
	}
	
}

package com.ssafy.backend.model.service;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.backend.model.ConferenceDto;
import com.ssafy.backend.model.ConferenceHistoryDto;
import com.ssafy.backend.model.ConferenceInfoDto;
import com.ssafy.backend.model.ConferenceTypeDto;
import com.ssafy.backend.model.EnterWrapperDto;
import com.ssafy.backend.model.UserDto;

public interface ConferenceService {
	boolean createConference(ConferenceDto conferenceDto) throws Exception;
	boolean closeConference(ConferenceDto conferenceDto) throws Exception;
	
	List<ConferenceTypeDto> getConferenceCategory() throws Exception; 
	
	ConferenceInfoDto getConferenceInfo(int idconference) throws Exception;
	
	boolean updateConferenceInfo(ConferenceInfoDto conferenceInfoDto) throws Exception;
	
	List<ConferenceDto> getConference() throws Exception;
	List<ConferenceDto> getConferenceBySort(@RequestParam String sort, @RequestParam String asc) throws Exception;
	List<ConferenceDto> searchByTitle(@RequestParam String word) throws Exception;
	List<ConferenceDto> getConferenceByCategory(@RequestParam String categoryType) throws Exception;
	
	boolean enterConference(@RequestBody EnterWrapperDto enterWrapper) throws Exception;
	
	boolean createConferenceHistory(@RequestBody ConferenceHistoryDto conferenceHistoryDto) throws Exception;
}

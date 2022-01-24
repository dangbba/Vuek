package com.ssafy.backend.model.service;


import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.backend.model.ConferenceDto;
import com.ssafy.backend.model.ConferenceTypeDto;

public interface ConferenceService {
	boolean createConference(ConferenceDto conferenceDto) throws Exception;
	boolean closeConference(ConferenceDto conferenceDto) throws Exception;
	
	List<ConferenceTypeDto> getConferenceCategory() throws Exception; 
	
	ConferenceDto getConferenceInfo(int idconference) throws Exception;
	
	boolean updateConferenceInfo(ConferenceDto conferenceDto) throws Exception;
	
	List<ConferenceDto> getConference() throws Exception;
	List<ConferenceDto> getConferenceBySort(@RequestParam String sort, @RequestParam String asc) throws Exception;
	List<ConferenceDto> searchByTitle(@RequestParam String word) throws Exception;
	List<ConferenceDto> getConferenceByCategory(@RequestParam String categoryType) throws Exception;
}

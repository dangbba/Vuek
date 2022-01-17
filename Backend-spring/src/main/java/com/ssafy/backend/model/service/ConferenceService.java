package com.ssafy.backend.model.service;


import com.ssafy.backend.model.ConferenceDto;
import com.ssafy.backend.model.ConferenceTypeDto;

public interface ConferenceService {
	boolean createConference(ConferenceDto conferenceDto) throws Exception;
	
	ConferenceTypeDto getConferenceCategory(int idconference_type) throws Exception; 
}

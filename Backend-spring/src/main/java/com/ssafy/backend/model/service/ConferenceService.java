package com.ssafy.backend.model.service;


import java.util.List;

import com.ssafy.backend.model.ConferenceDto;
import com.ssafy.backend.model.ConferenceTypeDto;

public interface ConferenceService {
	boolean createConference(ConferenceDto conferenceDto) throws Exception;
	
	List<ConferenceTypeDto> getConferenceCategory() throws Exception; 
}

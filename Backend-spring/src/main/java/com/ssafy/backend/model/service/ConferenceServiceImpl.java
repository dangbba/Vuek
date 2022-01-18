package com.ssafy.backend.model.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.backend.model.ConferenceDto;
import com.ssafy.backend.model.ConferenceTypeDto;
import com.ssafy.backend.model.mapper.ConferenceMapper;

@Service
public class ConferenceServiceImpl implements ConferenceService {

	@Autowired
	private ConferenceMapper conferenceMapper;
	
	@Override
	public boolean createConference(ConferenceDto conferenceDto) throws Exception {
		return conferenceMapper.createConference(conferenceDto);
	}
	
	@Override
	public List<ConferenceTypeDto> getConferenceCategory() throws Exception {
		return conferenceMapper.getConferenceCategory();
	}

}

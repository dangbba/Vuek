package com.ssafy.backend.model.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@Override
	public ConferenceDto getConferenceInfo(int idconference) throws Exception {
		return conferenceMapper.getConferenceInfo(idconference);
	}
	
	@Override
	public boolean updateConferenceInfo(ConferenceDto conferenceDto) throws Exception {
		return conferenceMapper.updateConferenceInfo(conferenceDto);
	}
	
	@Override
	public List<ConferenceDto> getConference() throws Exception {
		return conferenceMapper.getConference();
	}
	
	@Override
	public List<ConferenceDto> getConferenceBySort(@RequestParam String sort, @RequestParam String asc) throws Exception {
		return conferenceMapper.getConferenceBySort(sort, asc);
	}
	@Override
	public List<ConferenceDto> searchByTitle(@RequestParam String word) throws Exception {
		return conferenceMapper.searchByTitle(word);
	}
	
	@Override
	public List<ConferenceDto> getConferenceByCategory(@RequestParam String categoryType) throws Exception {
		return conferenceMapper.getConferenceByCategory(categoryType);
	}
}

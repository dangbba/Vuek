package com.ssafy.backend.model.service;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.backend.model.ConferenceDto;
import com.ssafy.backend.model.ConferenceHistoryDto;
import com.ssafy.backend.model.ConferenceInfoDto;
import com.ssafy.backend.model.ConferenceTypeDto;
import com.ssafy.backend.model.EnterWrapperDto;
import com.ssafy.backend.model.UserDto;
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
	public boolean closeConference(ConferenceDto conferenceDto) throws Exception {
		return conferenceMapper.closeConference(conferenceDto);
	}
	
	@Override
	public List<ConferenceTypeDto> getConferenceCategory() throws Exception {
		return conferenceMapper.getConferenceCategory();
	}
	
	@Override
	public ConferenceInfoDto getConferenceInfo(int idconference) throws Exception {
		return conferenceMapper.getConferenceInfo(idconference);
	}
	
	@Override
	public boolean updateConferenceInfo(ConferenceInfoDto conferenceInfoDto) throws Exception {
		return conferenceMapper.updateConferenceInfo(conferenceInfoDto);
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
	
	@Override
	public boolean enterConference(@RequestBody EnterWrapperDto enterWrapperDto) throws Exception {
		return conferenceMapper.enterConference(enterWrapperDto);
	}
	
	@Override
	public boolean createConferenceHistory(@RequestBody ConferenceHistoryDto conferenceHistoryDto) throws Exception {
		return conferenceMapper.createConferenceHistory(conferenceHistoryDto);
	}
	
	@Override
	public List<ConferenceHistoryDto> getConferenceHistory(@RequestParam String user_id) throws Exception {
		return conferenceMapper.getConferenceHistory(user_id);
	}
}

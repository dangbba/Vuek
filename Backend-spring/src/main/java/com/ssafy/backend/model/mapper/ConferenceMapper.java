package com.ssafy.backend.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.backend.model.ConferenceDto;
import com.ssafy.backend.model.ConferenceTypeDto;

@Mapper
public interface ConferenceMapper {
	boolean createConference(ConferenceDto conferenceDto) throws SQLException;
	boolean closeConference(ConferenceDto conferenceDto) throws SQLException;
	
	List<ConferenceTypeDto> getConferenceCategory() throws SQLException;
	
	ConferenceDto getConferenceInfo(int idconference) throws SQLException;
	
	boolean updateConferenceInfo(ConferenceDto conferenceDto) throws SQLException;
	
	List<ConferenceDto> getConference() throws SQLException;
	List<ConferenceDto> getConferenceBySort(@RequestParam String sort, @RequestParam String asc) throws SQLException;
	List<ConferenceDto> searchByTitle(@RequestParam String word) throws SQLException;
	List<ConferenceDto> getConferenceByCategory(@RequestParam String categoryType) throws SQLException;
	
}

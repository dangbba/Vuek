package com.ssafy.backend.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
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

@Mapper
public interface ConferenceMapper {
	boolean createConference(ConferenceDto conferenceDto) throws SQLException;
	boolean closeConference(ConferenceDto conferenceDto) throws SQLException;
	
	List<ConferenceTypeDto> getConferenceCategory() throws SQLException;
	
	ConferenceInfoDto getConferenceInfo(int idconference) throws SQLException;
	
	boolean updateConferenceInfo(ConferenceInfoDto conferenceInfoDto) throws SQLException;
	
	List<ConferenceDto> getConference() throws SQLException;
	List<ConferenceDto> getConferenceBySort(@RequestParam String sort, @RequestParam String asc) throws SQLException;
	List<ConferenceDto> searchByTitle(@RequestParam String word) throws SQLException;
	List<ConferenceDto> getConferenceByCategory(@RequestParam String categoryType) throws SQLException;
	
	boolean enterConference(@RequestBody EnterWrapperDto enterWrapperDto) throws SQLException;
	
	boolean createConferenceHistory(@RequestBody ConferenceHistoryDto conferenceHistoryDto) throws SQLException;
	
}

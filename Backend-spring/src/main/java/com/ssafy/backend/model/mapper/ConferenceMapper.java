package com.ssafy.backend.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.backend.model.ConferenceDto;
import com.ssafy.backend.model.ConferenceTypeDto;

@Mapper
public interface ConferenceMapper {
	boolean createConference(ConferenceDto conferenceDto) throws SQLException;
	
	ConferenceTypeDto getConferenceCategory(int idconference_type) throws SQLException; 
}

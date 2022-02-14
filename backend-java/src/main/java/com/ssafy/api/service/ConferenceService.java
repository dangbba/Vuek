package com.ssafy.api.service;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ssafy.db.dto.ConferenceInfoDto;
import com.ssafy.db.dto.EnterWrapperDto;
import com.ssafy.db.entity.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ConferenceService {
	long createConference(Conference conference) throws Exception;

	void closeConference(Conference conference) throws Exception;

	List<ConferenceType> getConferenceCategory() throws Exception;

	Conference getConferenceInfo(int idconference) throws Exception;

	void updateConferenceInfo(Conference conference) throws Exception;

	List<Conference> getConference() throws Exception;

	List<Conference> getConferenceBySort(@RequestParam String sort, @RequestParam String asc) throws Exception;

	List<Conference> searchByTitle(@RequestParam String word) throws Exception;

	List<Conference> getConferenceByCategory(@RequestParam int conference_type_id) throws Exception;

	void enterConference(@RequestBody EnterWrapperDto enterWrapperDto) throws Exception;

	void createConferenceHistory(@RequestBody ConferenceHistory conferenceHistory) throws Exception;

	List<ConferenceHistory> getConferenceHistory(@RequestParam String user_id) throws Exception;

	boolean deleteConference(Conference conference) throws Exception;

	int countNumOfPeople(@PathVariable int idconference) throws Exception;

	void participantClose(@PathVariable int idconference, @PathVariable String user_id) throws Exception;

	List<User> getParticipants(@PathVariable int idconference) throws Exception;

	List<Conference> getConferencesLimit(@RequestParam("limit") int limit) throws Exception;
}
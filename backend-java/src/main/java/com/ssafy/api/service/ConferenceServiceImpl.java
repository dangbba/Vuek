package com.ssafy.api.service;


import java.util.List;

import com.ssafy.db.dto.EnterWrapperDto;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.ConferenceHistoryRepository;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.ConferenceRepositorySupport;
import com.ssafy.db.repository.MarathonRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Service
public class ConferenceServiceImpl implements ConferenceService {

	@Autowired
	private ConferenceRepository conferenceRepository;

	@Autowired
	private ConferenceHistoryRepository conferenceHistoryRepository;

	@Autowired
	private ConferenceRepositorySupport conferenceRepositorySupport;

	@Autowired
	private MarathonRepositorySupport marathonRepositorySupport;

	@Transactional
	@Override
	public long createConference(Conference conference) throws Exception {
		conferenceRepository.save(conference);
		conferenceRepositorySupport.uploadUserConferenceId(conference);
		marathonRepositorySupport.plusNowPages(conference.getUser().getUserId());
		return conference.getId();
	}

	@Override
	public void closeConference(Conference conference) throws Exception {
		conferenceRepositorySupport.closeConference(conference);
		conferenceRepositorySupport.deleteUserConference(conference);
	}

	@Override
	public List<ConferenceType> getConferenceCategory() throws Exception {
		return conferenceRepositorySupport.getConferenceCategory();
	}

	@Override
	public Conference getConferenceInfo(int idconference) throws Exception {
		return conferenceRepositorySupport.getConferenceInfo(idconference);
	}

	@Override
	public void updateConferenceInfo(Conference conference) throws Exception {
		conferenceRepositorySupport.updateConferenceInfo(conference);
	}

	@Override
	public List<Conference> getConference() throws Exception {
		return conferenceRepositorySupport.getConferences();
	}

	@Override
	public List<Conference> getConferenceBySort(@RequestParam String sort, @RequestParam String asc) throws Exception {
		if(sort.equals("title")) {
			return conferenceRepositorySupport.findAllOrderByTitle(asc);
		}
		else {
			return conferenceRepositorySupport.findAllOrderByCallStartTime(asc);
		}

	}

	@Override
	public List<Conference> searchByTitle(@RequestParam String word) throws Exception {
		return conferenceRepositorySupport.getConferenceByTitle(word);
	}

	@Override
	public List<Conference> getConferenceByCategory(@RequestParam int conference_type_id) throws Exception {
		return conferenceRepositorySupport.getConferenceByCategory(conference_type_id);
	}

	@Transactional
	@Override
	public void enterConference(@RequestBody EnterWrapperDto enterWrapperDto) throws Exception {
		conferenceRepositorySupport.enterConference(enterWrapperDto);
		marathonRepositorySupport.plusNowPages(enterWrapperDto.getUserId());
//		marathonRepositorySupport.plusNowPages(enterWrapperDto.getUser().getUserId());
	}

	@Override
	public void createConferenceHistory(@RequestBody ConferenceHistory conferenceHistory) throws Exception {
		conferenceHistoryRepository.save(conferenceHistory);
	}

	@Override
	public List<ConferenceHistory> getConferenceHistory(@RequestParam String user_id) throws Exception {
		return conferenceRepositorySupport.getConferenceHistoryByUserId(user_id);
	}

	@Override
	@Transactional
	public boolean deleteConference(Conference conference) {
		try {
			conferenceRepository.deleteById(conference.getId());
			conferenceRepositorySupport.deleteUserConference(conference);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int countNumOfPeople(@PathVariable int idconference){
		return conferenceRepositorySupport.countNumOfPeople(idconference);
	}

	@Override
	public void participantClose(@PathVariable int idconference, @PathVariable String user_id){
		conferenceRepositorySupport.participantClose(idconference, user_id);
	}

	@Override
	public List<User> getParticipants(@PathVariable int idconference) {
		return conferenceRepositorySupport.getParticipants(idconference);
	}

	@Override
	public List<Conference> getConferencesLimit(@RequestParam("limit") int limit){
		return conferenceRepositorySupport.getConferencesLimit(limit);
	}
}

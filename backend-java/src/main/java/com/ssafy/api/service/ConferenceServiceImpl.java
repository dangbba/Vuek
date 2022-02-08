package com.ssafy.api.service;


import java.util.List;
import java.util.Optional;

import com.ssafy.db.dto.ConferenceInfoDto;
import com.ssafy.db.dto.EnterWrapperDto;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceHistory;
import com.ssafy.db.entity.ConferenceType;
import com.ssafy.db.repository.ConferenceHistoryRepository;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.ConferenceRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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

	@Override
	public long createConference(Conference conference) throws Exception {
		conferenceRepository.save(conference);
		return conference.getId();
	}
	
	@Override
	public void closeConference(Conference conference) throws Exception {
		conferenceRepositorySupport.closeConference(conference);
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
	
	@Override
	public void enterConference(@RequestBody EnterWrapperDto enterWrapperDto) throws Exception {
		conferenceRepositorySupport.enterConference(enterWrapperDto);
	}
	
	@Override
	public void createConferenceHistory(@RequestBody ConferenceHistory conferenceHistory) throws Exception {
//		conferenceRepositorySupport.createConferenceHistory(conferenceHistory);
		conferenceHistoryRepository.save(conferenceHistory);
	}
	
	@Override
	public List<ConferenceHistory> getConferenceHistory(@RequestParam String user_id) throws Exception {
		return conferenceRepositorySupport.findConferenceHistoryByUserId(user_id);
	}

	@Override
	public boolean deleteConference(Conference conference) {
		try {
			conferenceRepository.deleteById(conference.getId());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

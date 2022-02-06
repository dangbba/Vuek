package com.ssafy.api.service;


import java.util.List;
import java.util.Optional;

import com.ssafy.db.dto.ConferenceInfoDto;
import com.ssafy.db.dto.EnterWrapperDto;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceHistory;
import com.ssafy.db.entity.ConferenceType;
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

		//		if(sort.equals("title") || asc.equals("asc")){
//			Sort sortTitle = SortByTitleAsc();
//			return conferenceRepository.findAllOrderByTitle(sort, sortTitle);
//		}else if(sort.equals("title") || asc.equals("desc")){
//			Sort sortTitle = SortByTitleDesc();
//			return conferenceRepository.findAllOrderByTitle(sort, sortTitle);
//		}else if(sort.equals("callStartTime") || asc.equals("asc")){
//			Sort sortCallStartTime = SortByCallStartTimeAsc();
//			return conferenceRepository.findAllOrderByCallStartTime(sort, sortCallStartTime);
//		}else {
//			Sort sortCallStartTime = SortByCallStartTimeDesc();
//			return conferenceRepository.findAllOrderByCallStartTime(sort, sortCallStartTime);
//		}
	}

//	private Sort SortByTitleAsc() {
//		return Sort.by(Sort.Direction.ASC, "title");
//	}
//	private Sort SortByTitleDesc() {
//		return Sort.by(Sort.Direction.DESC, "title");
//	}
//	private Sort SortByCallStartTimeAsc() {
//		return Sort.by(Sort.Direction.ASC, "call_start_time");
//	}
//	private Sort SortByCallStartTimeDesc() {
//		return Sort.by(Sort.Direction.DESC, "call_start_time");
//	}

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
		conferenceRepositorySupport.createConferenceHistory(conferenceHistory);
	}
	
	@Override
	public List<ConferenceHistory> getConferenceHistory(@RequestParam String user_id) throws Exception {
		return conferenceRepositorySupport.findConferenceHistoryByUserId(user_id);
	}

	@Override
	public boolean deleteConference(Conference conference) {
		try {
			conferenceRepository.delete(conference);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

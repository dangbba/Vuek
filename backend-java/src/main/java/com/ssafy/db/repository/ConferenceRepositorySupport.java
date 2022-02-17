package com.ssafy.db.repository;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.CollectionPath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.ssafy.db.dto.ConferenceInfoDto;
import com.ssafy.db.dto.EnterWrapperDto;
import com.ssafy.db.entity.*;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import static com.ssafy.db.entity.QConference.conference;

@Repository
@RequiredArgsConstructor
public class ConferenceRepositorySupport {
	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	@Transactional
	@Modifying
	public void closeConference(Conference conference) {
		conference.callEndTime();
		conference.setIsActive(0);
		Query query = em.createQuery("update Conference c set c.callEndTime = :callEndTime, isActive = :isActive where c.id = :conferenceId")
				.setParameter("conferenceId", conference.getId())
				.setParameter("callEndTime", conference.getCallEndTime())
				.setParameter("isActive", conference.getIsActive());
		query.executeUpdate();
		em.close();
	}

	public List<ConferenceType> getConferenceCategory() {
		List<ConferenceType> list =  em.createQuery("select c from ConferenceType c", ConferenceType.class).getResultList();
		em.close();
		return list;
	}

	@Modifying
	@Transactional
	public Conference getConferenceInfo(int idconference) {

		QConference qConference = conference;
		Conference con = (Conference) queryFactory
				.from(qConference)
				.where(qConference.conference.id.eq((long) idconference))
				.fetchOne();

		return con;
	}

	@Modifying
	@Transactional
	public void updateConferenceInfo(Conference conference) {
		Query query = em.createNativeQuery("update Conference set conferenceTypeId = :conferenceTypeId, bookDetailId = :bookDetailId, thumbnailUrl = :thumbnailUrl, title = :title, isActive = :isActive, description = :description where conferenceId = :conferenceId", Conference.class)
				.setParameter("conferenceId", conference.getId())
				.setParameter("conferenceTypeId", conference.getConferenceType().getId())
				.setParameter("bookDetailId", conference.getBookDetail().getId())
				.setParameter("thumbnailUrl", conference.getThumbnailUrl())
				.setParameter("title", conference.getTitle())
				.setParameter("isActive", 1)
				.setParameter("description", conference.getDescription());
		query.executeUpdate();
		em.close();
	}

	public List<Conference> getConferenceByCategory(@RequestParam int conference_type_id) {
		return (List<Conference>) queryFactory
				.from(conference)
				.where(conference.conferenceType.id.eq(conference_type_id))
				.fetch();

	}

	@Modifying
	@Transactional
	public void enterConference(@RequestBody EnterWrapperDto enterWrapperDto) {
		QUserConference qUserConference = QUserConference.userConference;
		UserConference con = (UserConference) queryFactory
				.from(qUserConference)
				.where(qUserConference.conference.id.eq((long) enterWrapperDto.getIdconference()))
				.where(qUserConference.user.userId.eq(enterWrapperDto.getUserId()))
				.fetchOne();

		if(con == null) {
			Query query = em.createNativeQuery(
							"insert into " +
									"UserConference (conferenceId, userId) " +
									"values (:conferenceId, :userId)")
					.setParameter("conferenceId", enterWrapperDto.getIdconference())
					.setParameter("userId", enterWrapperDto.getUserId());
//				.setParameter("conferenceId", enterWrapperDto.getConferenceInfoDto().getId())
//				.setParameter("userId", enterWrapperDto.getUser().getUserId());
			query.executeUpdate();
		}
		em.close();

	}
	@Modifying
	@Transactional
	public void uploadUserConferenceId(Conference conference) {
		QConference qConference = QConference.conference;

		Query query = em.createNativeQuery(
						"insert into " +
								"UserConference (conferenceId, userId) " +
								"values (:conferenceId, :userId)")
				.setParameter("conferenceId", conference.getId())
				.setParameter("userId", conference.getUser().getUserId());
		query.executeUpdate();
		em.close();

	}
	public List<ConferenceHistory> getConferenceHistoryByUserId(String user_id) {
		QConferenceHistory qConferenceHistory = QConferenceHistory.conferenceHistory;
		List<ConferenceHistory> list = queryFactory
				.select(qConferenceHistory)
				.from(qConferenceHistory)
				.where(qConferenceHistory.user.userId.eq(user_id))
				.orderBy(qConferenceHistory.id.desc())
				.fetch();

		return list;
	}

	public List<Conference> getConferenceByTitle(@Param("word") String word) {
		QConference qConference = conference;
		List<Conference> con = (List<Conference>) queryFactory
				.from(qConference)
				.where(qConference.title.like(Expressions.stringTemplate("'%'")
						.concat(word)
						.concat(Expressions.stringTemplate("'%'"))))
				.fetch();
		return con;
	}

	public List<Conference> getConferences() {
		QConference qConference = conference;
		List<Conference> con = (List<Conference>) queryFactory
				.from(qConference)
				.fetch();
		return con;
	}


	public List<Conference> findAllOrderByTitle(String asc) {
		if (asc.equals("asc")) {
			QConference qConference = conference;
			List<Conference> con = (List<Conference>) queryFactory
					.from(qConference)
					.orderBy(qConference.title.asc())
					.fetch();
			return con;
		} else {
			QConference qConference = conference;
			List<Conference> con = (List<Conference>) queryFactory
					.from(qConference)
					.orderBy(qConference.title.desc())
					.fetch();
			return con;
		}
	}

	public List<Conference> findAllOrderByCallStartTime(String asc) {
		if (asc.equals("asc")) {
			QConference qConference = conference;
			List<Conference> con = (List<Conference>) queryFactory
					.from(qConference)
					.orderBy(qConference.callStartTime.asc())
					.fetch();
			return con;
		} else {
			QConference qConference = conference;
			List<Conference> con = (List<Conference>) queryFactory
					.from(qConference)
					.orderBy(qConference.callStartTime.desc())
					.fetch();
			return con;
		}
	}

	public int countNumOfPeople(@PathVariable int idconference){
		Query query = em.createNativeQuery(
						"select count(userId) " +
								"from UserConference "+
								"where conferenceId = :conferenceId")
				.setParameter("conferenceId", idconference);
		int num = Integer.parseInt(String.valueOf(query.getSingleResult())) ;
		em.close();

		return num;
	}

	@Modifying
	@Transactional
	public void participantClose(@PathVariable int idconference, @PathVariable String user_id){
		Query query = em.createNativeQuery(
						"delete from UserConference where conferenceId = :conferenceId " +
								"and userId = :userId")
				.setParameter("conferenceId", idconference)
				.setParameter("userId", user_id);
		query.executeUpdate();
		em.close();
	}

	public List<User> getParticipants(@PathVariable int idconference){
		Query query = em.createNativeQuery(
						"select User.userName " +
								"from UserConference join User " +
								"on UserConference.userId = User.userId " +
								"where conferenceId = :conferenceId")
				.setParameter("conferenceId", idconference);
		List<User> list = query.getResultList();
		em.close();

		return list;
	}

	@Modifying
	@Transactional
	public void deleteUserConference(Conference	conference) {
		Query query2 = em.createNativeQuery(
						"delete from UserConference where conferenceId = :conferenceId " +
								"and userId = :userId")
				.setParameter("conferenceId", conference.getId())
				.setParameter("userId", conference.getUser().getUserId());
		query2.executeUpdate();
		em.close();
	}

	public List<Conference> getConferencesLimit(int limit){
		QConference qConference = conference;
		List<Conference> list = (List<Conference>) queryFactory
				.from(qConference)
				.limit(limit)
				.orderBy(qConference.id.desc())
				.fetch();

		return list;
	}
}


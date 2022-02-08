package com.ssafy.db.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.querydsl.core.Tuple;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.ssafy.db.dto.EnterWrapperDto;
import com.ssafy.db.entity.*;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
		Query query = em.createNativeQuery("update Conference set conferenceTypeId = :conferenceTypeId, bookDetailId = :bookDetailId, thumbnailUrl = :thumbnailUrl, title = :title, description = :description where conferenceId = :conferenceId", Conference.class)
				.setParameter("conferenceId", conference.getId())
				.setParameter("conferenceTypeId", conference.getConferenceType().getId())
				.setParameter("bookDetailId", conference.getBookDetail().getId())
				.setParameter("thumbnailUrl", conference.getThumbnailUrl())
				.setParameter("isActive", conference.getIsActive())
				.setParameter("title", conference.getTitle())
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
		QConference qConference = QConference.conference;
		JPAUpdateClause updateClause = new JPAUpdateClause(em, qConference);

		Conference con = (Conference) queryFactory
				.from(qConference)
				.where(qConference.id.eq((long) enterWrapperDto.getConferenceInfoDto().getId()))
				.fetchOne();

		Query query = em.createNativeQuery(
						"insert into " +
								"conference_participant (Conference_conferenceId, participant_userId) " +
								"values (:Conference_conferenceId, :participant_userId)")
				.setParameter("Conference_conferenceId", enterWrapperDto.getConferenceInfoDto().getId())
				.setParameter("participant_userId", Collections.singletonList(enterWrapperDto.getUser()));
		query.executeUpdate();
		em.close();

	}

	@Transactional
	@Modifying
	public void createConferenceHistory(ConferenceHistory conferenceHistory) {
		conferenceHistory.insertedTime();
		Query query = em.createNativeQuery(
						"insert into " +
								"conferenceHistory (conferenceHistoryId, conferenceId, userId, action, insertedTime) " +
								"values (:conferenceHistoryId, :conferenceId, :userId," +
								":action, :insertedTime)")
				.setParameter("conferenceHistoryId", conferenceHistory.getId())
				.setParameter("conferenceId", conferenceHistory.getConference().getId())
				.setParameter("userId", conferenceHistory.getUser().getUserId())
				.setParameter("action", conferenceHistory.getAction())
				.setParameter("insertedTime", conferenceHistory.getInsertedTime());
		query.executeUpdate();
		em.close();
	}

	public List<ConferenceHistory> findConferenceHistoryByUserId(String user_id) {
		List<ConferenceHistory> list = em.createNativeQuery("select conferenceHistoryId, conferenceId, userId, action, insertedTime from conferenceHistory  where userId = :userId", ConferenceHistory.class)
				.setParameter("userId", user_id).getResultList();
		em.close();
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

}


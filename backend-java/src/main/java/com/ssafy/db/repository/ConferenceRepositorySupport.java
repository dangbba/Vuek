package com.ssafy.db.repository;

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
	public Long createConference(Conference conference) {
		conference.setParticipant(conference.getUser().getUserId());
		Query query = em.createQuery(
						"insert into" +
								"conference (conference_id, user_id, book_detail_id, conference_type_id," +
								"thumbnail_url, title, is_active, participant, call_start_time, description)" +
								"values (:conference_id, :user_id," +
								":book_detail_id, :conference_type_id, :thumbnail_url," +
								":title, :is_active, :participant, :call_start_time, :description)")
				.setParameter("conference_id", conference.getId())
				.setParameter("user_id", conference.getUser().getUserId())
				.setParameter("book_detail_id", conference.getBookDetail().getId())
				.setParameter("conference_type_id", conference.getConferenceType().getId())
				.setParameter("thumbnail_url", conference.getThumbnail_url())
				.setParameter("title", conference.getTitle())
				.setParameter("is_active", conference.getIs_active())
				.setParameter("participant", conference.getParticipant())
				.setParameter("call_start_time", conference.getCallStartTime())
				.setParameter("description", conference.getDescription());
		query.executeUpdate();

		return conference.getId();
	}

	@Transactional
	@Modifying
	public void closeConference(Conference conference) {
		conference.callEndTime();
		conference.setIs_active(0);
		Query query = em.createQuery("update Conference c set c.callEndTime = :call_end_time, is_active = :is_active where c.id = :conference_id")
				.setParameter("conference_id", conference.getId())
				.setParameter("call_end_time", conference.getCallEndTime())
				.setParameter("is_active", conference.getIs_active());
		query.executeUpdate();
	}

	public List<ConferenceType> getConferenceCategory() {
		return em.createQuery("select c from ConferenceType c", ConferenceType.class).getResultList();
	}

	public Conference getConferenceInfo(int idconference) {
		QConference qConference = conference;
		Conference con = (Conference) queryFactory
				.from(qConference)
				.where(qConference.conference.id.eq((long) idconference))
				.fetchOne();

//		List<Conference> list = em.createNativeQuery("select conference_id, user_id, book_detail_id, conference_type_id, call_start_time, thumbnail.url, title, is_active, participant from Conference where conference_id = :conference_id", Conference.class)
//				.setParameter("conference_id", idconference).getResultList();
//		JpaResultMapper jpaResultMapper = new JpaResultMapper();
//		Conference conference = jpaResultMapper.uniqueResult(nativeQuery, Conference.class);
//		conferenceInfoDto.setConference_type(conference.getConferenceType().getId());
//		conferenceInfoDto.setBook_detail_id(conference.getBookDetail().getId());
//		conferenceInfoDto.setId(idconference);
//		conferenceInfoDto.setParticipant(conference.getParticipant());
//		conferenceInfoDto.setIs_active(conference.getIs_active());
//		conferenceInfoDto.setTitle(conference.getTitle());
//		conferenceInfoDto.setCall_start_time(conference.getCallStartTime().toString());
//		conferenceInfoDto.setThumbnail_url(conference.getThumbnail_url());
//		conferenceInfoDto.setUser_id(conference.getUser().getUserId());
		return con;
	}

	@Modifying
	@Transactional
	public void updateConferenceInfo(Conference conference) {
		Query query = em.createNativeQuery("update Conference set conference_type_id = :conference_type_id, book_detail_id = :book_detail_id, thumbnail_url = :thumbnail_url, title = :title, is_active = :is_active, description = :description where conference_id = :conference_id", Conference.class)
				.setParameter("conference_id", conference.getId())
				.setParameter("conference_type_id", conference.getConferenceType().getId())
				.setParameter("book_detail_id", conference.getBookDetail().getId())
				.setParameter("thumbnail_url", conference.getThumbnail_url())
				.setParameter("title", conference.getTitle())
				.setParameter("is_active", conference.getIs_active())
				.setParameter("description", conference.getDescription());
		query.executeUpdate();
	}

//	public List<Conference> getConference() {
//		return em.createQuery("select c from conference c", Conference.class).getResultList();
//	}


//	public List<Conference> getConferenceBySort(@RequestParam String sort, @RequestParam String asc) {
//
//	}

//	public List<Conference> searchByTitle(@RequestParam String word) throws SQLException;

	public List<Conference> getConferenceByCategory(@RequestParam int conference_type_id) {
		return (List<Conference>) queryFactory
				.from(conference)
				.where(conference.conferenceType.id.eq(conference_type_id))
				.fetch();

//		return em.createNativeQuery("select conference_id, user_id, book_detail_id, conference_type_id, thumbnail_url, title, is_active from Conference where conference_type_id = :conference_type_id", Conference.class)
//				.setParameter("conference_type_id", conference_type_id).getResultList();
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

		long count = updateClause.where(qConference.id.eq((long) enterWrapperDto.getConferenceInfoDto().getId()))
				.set(qConference.participant, con.getParticipant().concat(", ").concat(enterWrapperDto.getUser().getUserId()))
				.execute();
//		Query query = em.createNativeQuery(
//				"update conference set participant = CONCAT(participant, ', ', :user_id) where conference_id = :id")
//						.setParameter("user_id", enterWrapperDto.getUser().getUserId())
//								.setParameter("id", enterWrapperDto.getConferenceInfoDto().getId());
//		query.executeUpdate();
	}

	@Transactional
	@Modifying
	public void createConferenceHistory(ConferenceHistory conferenceHistory) {
		conferenceHistory.inserted_time();
		Query query = em.createNativeQuery(
						"insert into " +
								"conference_history (conference_history_id, conference_id, user_id, action, inserted_time) " +
								"values (:conference_history_id, :conference_id, :user_id," +
								":action, :inserted_time)")
				.setParameter("conference_history_id", conferenceHistory.getId())
				.setParameter("conference_id", conferenceHistory.getConference().getId())
				.setParameter("user_id", conferenceHistory.getUser().getUserId())
				.setParameter("action", conferenceHistory.getAction())
				.setParameter("inserted_time", conferenceHistory.getInserted_time());
		query.executeUpdate();
	}

	public List<ConferenceHistory> findConferenceHistoryByUserId(String user_id) {
		return em.createNativeQuery("select conference_history_id, conference_id, user_id, action, inserted_time from conference_history  where user_id = :user_id", ConferenceHistory.class)
				.setParameter("user_id", user_id).getResultList();
	}

	///////////////////////////////////////////////////
//	@Query(value = "select c from conference c where c.title like concat('%', :title, '%')", nativeQuery = true)
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


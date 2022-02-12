package com.ssafy.db.repository;

import java.util.*;

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

//		QUserConference qUserConference = QUserConference.userConference;
//		List uc = queryFactory
//				.select(qUserConference.user)
//				.from(qUserConference)
//				.where(qUserConference.conference.id.eq((long) idconference))
//				.fetch();
//
//		Collections.singletonList(qConference.participant).addAll(uc);
//		JPAUpdateClause updateClause = new JPAUpdateClause(em, qConference);
//		long count = updateClause.where(qConference.id.eq((long) idconference))
//				.set(Collections.singletonList(qConference.participant).addAll(uc)).execute();

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
		QConference qConference = QConference.conference;

		Conference con = (Conference) queryFactory
				.from(qConference)
				.where(qConference.id.eq((long) enterWrapperDto.getConferenceInfoDto().getId()))
				.fetchOne();

		Query query = em.createNativeQuery(
						"insert into " +
								"Conference_participant (Conference_conferenceId, participant_userId) " +
								"values (:Conference_conferenceId, :participant_userId)")
				.setParameter("Conference_conferenceId", enterWrapperDto.getConferenceInfoDto().getId())
				.setParameter("participant_userId", enterWrapperDto.getUser().getUserId());
		query.executeUpdate();
		em.close();

	}
	@Modifying
	@Transactional
	public void uploadUserConferenceId(Conference conference) {
		QConference qConference = QConference.conference;

		Conference con = (Conference) queryFactory
				.from(qConference)
				.where(qConference.id.eq((long) conference.getId()))
				.fetchOne();

//		Query query = em.createNativeQuery(
//						"insert into " +
//								"Conference_participant (Conference_conferenceId, participant_userId) " +
//								"values (:Conference_conferenceId, :participant_userId)")
//				.setParameter("Conference_conferenceId", conference.getId())
//				.setParameter("participant_userId", conference.getUser().getUserId());
//		query.executeUpdate();
//		em.close();
		Query query = em.createNativeQuery(
						"insert into " +
								"UserConference (conferenceId, userId) " +
								"values (:conferenceId, :userId)")
				.setParameter("conferenceId", conference.getId())
				.setParameter("userId", conference.getUser().getUserId());
		query.executeUpdate();
		em.close();

	}
	public List<ConferenceHistory> findConferenceHistoryByUserId(String user_id) {
		QConferenceHistory qConferenceHistory = QConferenceHistory.conferenceHistory;
		List<ConferenceHistory> list = queryFactory.select(qConferenceHistory).from(qConferenceHistory)
				.where(qConferenceHistory.user.userId.eq(user_id)).fetch();

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
						"select count(participant_userId) " +
								"from Conference_participant "+
								"where Conference_conferenceId = :Conference_conferenceId")
				.setParameter("Conference_conferenceId", idconference);
		int num = Integer.parseInt(String.valueOf(query.getSingleResult()))+1 ;
		em.close();

		return num;
	}

	@Modifying
	@Transactional
	public void participantClose(@PathVariable int idconference, @PathVariable String user_id){
		Query query = em.createNativeQuery(
				"delete from Conference_participant where Conference_conferenceId = :Conference_conferenceId " +
						"and participant_userId = :participant_userId")
				.setParameter("Conference_conferenceId", idconference)
				.setParameter("participant_userId", user_id);
		query.executeUpdate();
		em.close();
	}

}


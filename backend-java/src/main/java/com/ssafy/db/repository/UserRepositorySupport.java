package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdateReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.User;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
@RequiredArgsConstructor
public class UserRepositorySupport {

    private final EntityManager em;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;

    public Optional<User> findUserByUserId(String user_id) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.userId.eq(user_id)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }

    @Modifying
    @Transactional
    public void updateUser(User user){
        QUser qUser = QUser.user;
        JPAUpdateClause updateClause = new JPAUpdateClause(em, qUser);
        long count = updateClause
                .set(qUser.userName, user.getUserName())
                .set(qUser.password, passwordEncoder.encode(user.getPassword()))
                .set(qUser.email, user.getEmail())
                .set(qUser.genre, user.getGenre())
                .set(qUser.goal, user.getGoal())
                .set(qUser.socialLink, user.getSocialLink())
                .set(qUser.profileImage, user.getProfileImage())
                .where(qUser.userId.eq(user.getUserId()))
                .execute();
        em.close();
    }

    @Modifying
    @Transactional
    public void deleteByUserId(String user_id) {

        String test = "jesonk3";
        System.out.println(user_id + " : user Id ,Support test!!!!!!!!!!!");
        Query conferencehistory = em.createNativeQuery(
                        "delete from ConferenceHistory where userId = :userId")
//                        "update conferencehistory set userId = null where userId = :userId")
                .setParameter("userId", user_id);
        conferencehistory.executeUpdate();
        em.close();

        Query conference = em.createNativeQuery(
                        "update Conference set userId = null, isActive = 0 where userId = :userId")
                .setParameter("userId", user_id);
        conference.executeUpdate();
        em.close();

        Query userconference = em.createNativeQuery(
                        "update UserConference set userId = null where userId = :userId")
                .setParameter("userId", user_id);
        userconference.executeUpdate();
        em.close();

        Query query = em.createNativeQuery(
                        "delete from User where userId = :userId")
                .setParameter("userId", user_id);
        query.executeUpdate();
        em.close();

        Query deleteHistoryNull = em.createNativeQuery(
                        "delete from ConferenceHistory where userId = :userId")
                .setParameter("userId", null);
        deleteHistoryNull.executeUpdate();
        em.close();
    }

//    @Modifying
//    @Transactional
//    public void updateUser(UserUpdateReq userUpdateReq) throws Exception {
//        String imgPath = null;
//        if (userUpdateReq.getProfileImage().isEmpty() == false) {
//            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
//            UUID uuid = UUID.randomUUID();
//            String fileName = uuid + "_" + userUpdateReq.getProfileImage().getOriginalFilename();
//            File saveFile = new File(projectPath + "/" + fileName);
//            if(saveFile.exists() == false) {
//                saveFile.mkdirs();
//            }
//            imgPath = "\\backend-java\\src\\main\\resources\\static\\files\\" + fileName;
//            userUpdateReq.getProfileImage().transferTo(saveFile);
//        }
//        QUser qUser = QUser.user;
//        JPAUpdateClause updateClause = new JPAUpdateClause(em, qUser);
//        long count = updateClause
//                .set(qUser.userName, userUpdateReq.getUser().getUserName())
////                .set(qUser.password, passwordEncoder.encode(userUpdateReq.getUser().getPassword()))
//                .set(qUser.email, userUpdateReq.getUser().getEmail())
//                .set(qUser.genre, userUpdateReq.getUser().getGenre())
//                .set(qUser.goal, userUpdateReq.getUser().getGoal())
//                .set(qUser.socialLink,userUpdateReq.getUser().getSocialLink())
//                .set(qUser.profileImage, imgPath)
//                .where(qUser.userId.eq(userUpdateReq.getUser().getUserId()))
//                .execute();
//        em.close();
//    }

}

package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdateReq;
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
    public void updateUser(UserUpdateReq userUpdateReq) throws Exception {
        String fileName = null;
        if (userUpdateReq.getProfileImage() != null) {
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resource\\static\\files";
            System.out.println("projectPath!!!!!!!!!!!!!1 : " + projectPath);
            System.out.println("requesst.getProfileImg : " + userUpdateReq.getProfileImage());
            System.out.println("request.getUser().getUserId() : " + userUpdateReq.getUser().getUserId());
            UUID uuid = UUID.randomUUID();
            fileName = uuid + "_" + userUpdateReq.getProfileImage().getOriginalFilename();
            File saveFile = new File(projectPath, "fileName");
            userUpdateReq.getProfileImage().transferTo(saveFile);
        }

        QUser qUser = QUser.user;
        JPAUpdateClause updateClause = new JPAUpdateClause(em, qUser);
        long count = updateClause
                .set(qUser.userName, userUpdateReq.getUser().getUserName())
                .set(qUser.password, passwordEncoder.encode(userUpdateReq.getUser().getPassword()))
                .set(qUser.email, userUpdateReq.getUser().getEmail())
                .set(qUser.genre, userUpdateReq.getUser().getGenre())
                .set(qUser.goal, userUpdateReq.getUser().getGoal())
                .set(qUser.socialLink,userUpdateReq.getUser().getSocialLink())
                .set(qUser.profileImage, fileName)
                .where(qUser.userId.eq(userUpdateReq.getUser().getUserId()))
                .execute();
        em.close();
    }

}

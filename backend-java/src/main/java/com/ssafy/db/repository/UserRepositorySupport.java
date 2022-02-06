package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.User;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
@RequiredArgsConstructor
public class UserRepositorySupport {

    private final EntityManager em;

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
                .set(qUser.password, user.getPassword())
                .where(qUser.userId.eq(user.getUserId()))
                .execute();
        em.close();
    }

    @Modifying
    @Transactional
    public void deleteUserByUserId(User user){
        QUser qUser = QUser.user;
        JPAUpdateClause deleteClause = new JPAUpdateClause(em, qUser);
        long count = deleteClause.where(qUser.userId.eq(user.getUserId()))
                        .execute();
        em.close();
    }

}

package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.ssafy.db.entity.Marathon;
import com.ssafy.db.entity.QMarathon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MarathonRepositorySupport {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public int getNowPages(@PathVariable String user_id){
        QMarathon qMarathon = QMarathon.marathon;
        int nowPages = queryFactory.select(qMarathon.nowPages).from(qMarathon).where(qMarathon.userId.eq(user_id)).fetchOne();
        return nowPages;
    }

    public void plusNowPages(@PathVariable String user_id){
        QMarathon qMarathon = QMarathon.marathon;
        JPAUpdateClause updateClause = new JPAUpdateClause(em, qMarathon);
        long count = updateClause.where(qMarathon.userId.eq(user_id))
                .set(qMarathon.nowPages, qMarathon.nowPages.add(1))
                .execute();
    }

}
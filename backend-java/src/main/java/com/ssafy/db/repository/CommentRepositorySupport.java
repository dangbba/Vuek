package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.QComment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
@RequiredArgsConstructor
public class CommentRepositorySupport {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Modifying
    @Transactional
    public void updateCommentOne(Comment comment) {
        comment.updated_at();
//        Query query = em.createNativeQuery("update Conference set comment_content = :comment_content, updated_at = :updated_at where comment_id = :comment_id", Comment.class)
//                .setParameter("comment_id", comment.getId())
//                .setParameter("comment_content", comment.getComment_content())
//                .setParameter("updated_at", comment.getUpdated_at());
//        query.executeUpdate();
        QComment qComment = QComment.comment;
        JPAUpdateClause updateClause = new JPAUpdateClause(em, qComment);
        long count = updateClause
                .set(qComment.comment_content, comment.getComment_content())
                .set(qComment.updated_at, comment.getUpdated_at())
                .where(qComment.id.eq(comment.getId()))
                .execute();
    }
}

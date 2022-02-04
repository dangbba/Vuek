package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAUpdateClause;
import com.ssafy.db.entity.QReview;
import com.ssafy.db.entity.Review;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Repository
@RequiredArgsConstructor
public class ReviewRepositorySupport {

    private final EntityManager em;
//    private  final EntityManagerFactory emf;

    @Transactional
    @Modifying
    public Review save(Review review){
        em.persist(review);
        return review;
    }

    @Transactional
    @Modifying
    public void createReview(Review review) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("review");
//        EntityManager entityManager = emf.createEntityManager();
//        EntityManager em = factory.createEntityManager();
        Query query = em.createNativeQuery(
                "insert into " +
                        "review (review_id, book_detail_id, user_id, review_content, review_name, created_at)" +
                        "values (:review_id, :book_detail_id, :user_id, :review_content, :review_name, :created_at")
                .setParameter("review_id", review.getId())
                .setParameter("book_detail_id", review.getBookDetail().getId())
                .setParameter("user_id", review.getUser().getUserId())
                .setParameter("review_content", review.getReview_content())
                .setParameter("review_name", review.getReview_name())
                .setParameter("created_at", review.getCreated_at());
        query.executeUpdate();
        em.close();
    }

    public Review findById(long id){
        Review review = em.find(Review.class, id);
        return review;
    }

    @Modifying
    @Transactional
    public void updateReviewOne(Review review) {
        review.updated_at();
        QReview qReview = QReview.review;
        JPAUpdateClause updateClause = new JPAUpdateClause(em, qReview);
        long count = updateClause
                .set(qReview.bookDetail.id, review.getBookDetail().getId())
                .set(qReview.review_content, review.getReview_content())
                .set(qReview.review_name, review.getReview_name())
                .set(qReview.updated_at, review.getUpdated_at())
                .where(qReview.id.eq(review.getId()))
                .execute();
        em.close();
    }
}

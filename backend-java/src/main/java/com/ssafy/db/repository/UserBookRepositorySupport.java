package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserBookRepositorySupport {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<BookDetail> getUserBooksByUserId(String userId){
        Query query = em.createNativeQuery(
                        "select BookDetail.bookDetailId, BookDetail.title, BookDetail.author, BookDetail.overview, BookDetail.price, BookDetail.publishDate, BookDetail.publisher, BookDetail.sailStatus, BookDetail.titleUrl, BookDetail.isbn " +
                                "from UserBook join BookDetail " +
                                "on UserBook.bookDetailId = BookDetail.bookDetailId " +
                                "where UserBook.userId = :userId")
                .setParameter("userId", userId);
        List<BookDetail> list = query.getResultList();
        em.close();
        
        return list;
    }

    @Modifying
    @Transactional
    public void createUserBook(String isbn, String userId){
        QBookDetail qBookDetail = QBookDetail.bookDetail;
        BookDetail book = (BookDetail) queryFactory
                .from(qBookDetail)
                .where(qBookDetail.isbn.eq(isbn))
                .fetchOne();

        Query query = em.createNativeQuery(
                        "insert into " +
                                "UserBook (bookDetailId, userId) " +
                                "values (:bookDetailId, :userId)")
                .setParameter("bookDetailId", book.getId())
                .setParameter("userId", userId);
        query.executeUpdate();
        em.close();
    }
}

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
        QBookDetail qBookDetail = QBookDetail.bookDetail;
        QUserBook qUserBook = QUserBook.userBook;
        List<BookDetail> list = (List<BookDetail>) queryFactory
                .select(qBookDetail)
                .from(qUserBook)
                .join(qBookDetail)
                .on(qUserBook.bookDetail.id.eq(qBookDetail.id))
                .where(qUserBook.user.userId.eq(userId))
                .fetch();

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

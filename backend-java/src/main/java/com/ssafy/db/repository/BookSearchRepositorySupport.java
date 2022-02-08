package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.BookDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import static com.ssafy.db.entity.QBookDetail.bookDetail;


@Repository
@RequiredArgsConstructor
public class BookSearchRepositorySupport {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Transactional
    @Modifying
    public void createBookDetail(BookDetail bookDetail) {
        Query query = em.createNativeQuery(
                        "insert into " +
                                "BookDetail (bookDetailId, title, author, overview, price, publishDate, publisher, sailStatus, titleUrl, isbn) " +
                                "values (:bookDetailId, :title, :author, :overview, :price, :publishDate, :publisher, " +
                                ":sailStatus, :titleUrl, :isbn)")
                .setParameter("bookDetailId", bookDetail.getId())
                .setParameter("title", bookDetail.getTitle())
                .setParameter("author", bookDetail.getAuthor())
                .setParameter("overview", bookDetail.getOverview())
                .setParameter("price", bookDetail.getPrice())
                .setParameter("publishDate", bookDetail.getPublishDate())
                .setParameter("publisher", bookDetail.getPublisher())
                .setParameter("sailStatus", bookDetail.getSailStatus())
                .setParameter("titleUrl", bookDetail.getTitleUrl())
                .setParameter("isbn", bookDetail.getIsbn());
        query.executeUpdate();
        em.close();
    }

    public boolean isExistBookDetail(String isbn) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(bookDetail)
                .where(bookDetail.isbn.eq(isbn))
                .fetchFirst();
        return fetchOne != null;
    }
}

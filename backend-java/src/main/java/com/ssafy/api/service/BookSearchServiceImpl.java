package com.ssafy.api.service;

import com.ssafy.db.entity.BookDetail;
import com.ssafy.db.repository.BookSearchRepository;
import com.ssafy.db.repository.BookSearchRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookSearchServiceImpl implements BookSearchService{

    @Autowired
    private BookSearchRepository bookSearchRepository;

    @Autowired
    private BookSearchRepositorySupport bookSearchRepositorySupport;

    @Override
    public String createBookDetail(BookDetail bookDetail) throws Exception {
        bookSearchRepositorySupport.createBookDetail(bookDetail);
        return bookDetail.getTitle();
    }

    @Override
    public Integer isExistBookDetail(String isbn) throws Exception {
        return bookSearchRepositorySupport.isExistBookDetail(isbn);
    }
}

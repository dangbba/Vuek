package com.ssafy.api.service;

import com.ssafy.db.entity.BookDetail;
import com.ssafy.db.repository.BookSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookSearchServiceImpl implements BookSearchService{

    @Autowired
    private BookSearchRepository bookSearchRepository;

    @Override
    public long createBookDetail(BookDetail bookDetail) throws Exception {
        bookSearchRepository.save(bookDetail);
        return bookDetail.getId();
    }
}

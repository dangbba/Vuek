package com.ssafy.api.service;

import com.ssafy.db.entity.BookDetail;

public interface BookSearchService {
    long createBookDetail(BookDetail bookdetail) throws Exception;
}

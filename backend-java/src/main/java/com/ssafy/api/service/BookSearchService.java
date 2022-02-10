package com.ssafy.api.service;

import com.ssafy.db.entity.BookDetail;

public interface BookSearchService {
    String createBookDetail(BookDetail bookdetail) throws Exception;
    Integer isExistBookDetail(String isbn) throws Exception;
}

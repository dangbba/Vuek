package com.ssafy.api.service;

import com.ssafy.db.entity.BookDetail;
import com.ssafy.db.entity.UserBook;

import java.util.List;

public interface UserBookService {
    void createUserBook(String isbn, String userId) throws Exception;
    List<BookDetail> getUserBooksByUserId(String userId) throws Exception;
}

package com.ssafy.api.service;

import com.ssafy.db.entity.UserBook;

import java.util.List;

public interface UserBookService {
    int createUserBook(UserBook userBook) throws Exception;
    List<UserBook> getUserBooksByUserId(String userId) throws Exception;
}

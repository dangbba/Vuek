package com.ssafy.api.service;

import com.ssafy.db.entity.UserBook;
import com.ssafy.db.repository.UserBookRepository;
import com.ssafy.db.repository.UserBookRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserBookServiceImpl implements UserBookService {
    @Autowired
    UserBookRepository userBookRepository;
    @Autowired
    UserBookRepositorySupport userBookRepositorySupport;

    @Override
    public int createUserBook(UserBook userBook) throws Exception {
        userBookRepository.save(userBook);
        return userBook.getId();
    }

    @Override
    public List<UserBook> getUserBooksByUserId(String userId) throws Exception {
        List<UserBook> list = userBookRepositorySupport.getUserBooksByUserId(userId);
        return null;
    }
}

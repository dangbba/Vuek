package com.ssafy.api.service;

import com.ssafy.db.entity.BookDetail;
import com.ssafy.db.entity.UserBook;
import com.ssafy.db.repository.UserBookRepository;
import com.ssafy.db.repository.UserBookRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBookServiceImpl implements UserBookService {
    @Autowired
    UserBookRepository userBookRepository;
    @Autowired
    UserBookRepositorySupport userBookRepositorySupport;

    @Override
    public void createUserBook(String isbn, String userId) throws Exception {
        userBookRepositorySupport.createUserBook(isbn, userId);
    }

    @Override
    public List<BookDetail> getUserBooksByUserId(String userId) throws Exception {
        List<BookDetail> list = userBookRepositorySupport.getUserBooksByUserId(userId);
        return list;
    }
}

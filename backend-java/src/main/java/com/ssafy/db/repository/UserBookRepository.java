package com.ssafy.db.repository;

import com.ssafy.db.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBookRepository extends JpaRepository<UserBook, Integer> {
}

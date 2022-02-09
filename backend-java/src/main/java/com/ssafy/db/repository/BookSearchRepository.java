package com.ssafy.db.repository;

import com.ssafy.db.entity.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookSearchRepository extends JpaRepository<BookDetail, Integer> {
}

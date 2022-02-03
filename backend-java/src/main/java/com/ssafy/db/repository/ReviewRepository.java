package com.ssafy.db.repository;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.db.entity.Review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review saveAndFlush(Review review);
    List<Review> findAll();
    void deleteById(int id);
}

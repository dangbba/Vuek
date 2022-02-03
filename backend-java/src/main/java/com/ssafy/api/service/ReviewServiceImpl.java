package com.ssafy.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ssafy.db.entity.Review;
import com.ssafy.db.repository.ReviewRepository;
import com.ssafy.db.repository.ReviewRepositorySupport;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired ReviewRepositorySupport reviewRepositorySupport;
	/////////////////////////////
	@Override
	public Review addReview(Review review) throws Exception {
//		reviewRepository.saveAndFlush(review);
		return reviewRepositorySupport.save(review);

	}
	
	@Override
	public List<Review> getReviews() throws Exception {
		return reviewRepository.findAll();
	}

	@Override
	public Review getReviewOne(long id) throws Exception {
		return reviewRepositorySupport.findById(id);
	}

	/////////////////////////
	@Transactional
	@Override
	public void updateReviewOne(Review review) throws Exception {
		reviewRepositorySupport.updateReviewOne(review);
//		Review findReview = getReviewOne(review.getId());
//		findReview.setReview_name(review.getReview_name());
//		findReview.setReview_content(review.getReview_content());
//		findReview.setBookDetail(review.getBookDetail());
//		findReview.setUpdated_at(LocalDateTime.now());
//		reviewRepository.saveAndFlush(findReview);
	}

	@Override
	public void deleteReview(long id) throws Exception {
		reviewRepository.deleteById(id);
	}
}
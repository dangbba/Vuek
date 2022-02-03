package com.ssafy.api.service;
import com.ssafy.db.entity.Review;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

	Review addReview(Review review) throws Exception;
	List<Review> getReviews() throws Exception;
	Review getReviewOne(long id) throws Exception;
	void deleteReview(long id) throws Exception;
	void updateReviewOne(Review review) throws Exception;
}
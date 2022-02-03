package com.ssafy.api.service;
import com.ssafy.db.entity.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
	
	void addReview(Review review) throws Exception;
	List<Review> getReviews() throws Exception;
	Review getReviewOne(long id) throws Exception;
	void deleteReview(int id) throws Exception;
	void updateReviewOne(Review review) throws Exception;
}
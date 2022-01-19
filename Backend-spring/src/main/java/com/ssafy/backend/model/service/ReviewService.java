package com.ssafy.backend.model.service;
import java.util.List;

import com.ssafy.backend.model.ReviewDto;

public interface ReviewService {
	
	boolean addReview(ReviewDto ReviewDto) throws Exception;
	List<ReviewDto> getReviews() throws Exception; 
	ReviewDto getReviewOne(int review_id) throws Exception;
	boolean deleteReview(int id) throws Exception;
	boolean updateReviewOne(ReviewDto ReviewDto) throws Exception;
}
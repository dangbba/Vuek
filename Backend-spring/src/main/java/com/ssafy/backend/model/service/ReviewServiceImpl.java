package com.ssafy.backend.model.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.backend.model.ConferenceDto;
import com.ssafy.backend.model.ReviewDto;
import com.ssafy.backend.model.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewMapper reviewMapper;
	
	@Override
	public boolean addReview(ReviewDto reviewDto) throws Exception {
		return reviewMapper.addReview(reviewDto);
	}
	
	@Override
	public List<ReviewDto> getReviews() throws Exception {
		return reviewMapper.getReviews();
	}
	
	@Override
	public ReviewDto getReviewOne(int id) throws Exception {
		return reviewMapper.getReviewOne(id);
	}
	@Override
	public boolean updateReviewOne(ReviewDto reviewDto) throws Exception {
		return reviewMapper.updateReviewOne(reviewDto);
	}
	@Override
	public boolean deleteReview(int id) throws Exception {
		return reviewMapper.deleteReview(id);
	}
}
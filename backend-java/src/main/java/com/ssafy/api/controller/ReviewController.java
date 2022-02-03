package com.ssafy.api.controller;

import java.util.List;

import com.ssafy.api.service.ReviewService;
import com.ssafy.db.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private ReviewService reviewService;
	
	@ApiOperation(value = "리뷰글을 추가하며, 성공/실패에 따라서 SUCCESS/FAIL", response = String.class)
	@PostMapping
	public ResponseEntity<String> addReview(@RequestBody Review review) throws Exception {
		reviewService.addReview(review);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);

	}
	
	@ApiOperation(value = "전체 리뷰를 조회하여 반환", response = String.class)
	@GetMapping("/index")
	public ResponseEntity<List<Review>> getReviews() throws Exception {
		return new ResponseEntity<>(reviewService.getReviews(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "단일 리뷰를 조회하여 반환", response = String.class)
	@GetMapping("/{review_id}")
	public ResponseEntity<Review> getReviewOne(@PathVariable long review_id) throws Exception {
		return new ResponseEntity<>(reviewService.getReviewOne(review_id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "리뷰를 삭제한다, SUCCESS/FAIL", response = String.class)
	@DeleteMapping("/{review_id}")
	public ResponseEntity<String> deleteReview(@PathVariable long review_id) throws Exception {
		reviewService.deleteReview(review_id);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@ApiOperation(value = "리뷰를 수정한다, SUCCESS/FAIL", response = String.class)
	@PutMapping("/{review_id}")
	public ResponseEntity<String> updateReviewOne(@RequestBody Review review) throws Exception {
		reviewService.updateReviewOne(review);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);

	}
	
}







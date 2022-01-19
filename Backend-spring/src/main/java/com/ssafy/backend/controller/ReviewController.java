package com.ssafy.backend.controller;

import java.util.List;

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

import com.ssafy.backend.model.ReviewDto;
import com.ssafy.backend.model.service.ReviewService;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/review")
public class ReviewController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private ReviewService reviewService;
	
	@ApiOperation(value = "리뷰글을 추가하며, 성공/실패에 따라서 SUCCESS/FAIL", response = String.class)
	@PostMapping
	public ResponseEntity<String> addReview(@RequestBody ReviewDto reviewDto) throws Exception {
		if (reviewService.addReview(reviewDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "전체 리뷰를 조회하여 반환", response = String.class)
	@GetMapping("/index")
	public ResponseEntity<List<ReviewDto>> getReviews() throws Exception {
		return new ResponseEntity<>(reviewService.getReviews(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "단일 리뷰를 조회하여 반환", response = String.class)
	@GetMapping("/{review_id}")
	public ResponseEntity<ReviewDto> getReviewOne(@PathVariable int review_id) throws Exception {
		return new ResponseEntity<>(reviewService.getReviewOne(review_id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "리뷰를 삭제한다, SUCCESS/FAIL", response = String.class)
	@DeleteMapping("/{review_id}")
	public ResponseEntity<String> deleteReview(@PathVariable int review_id) throws Exception {
		if (reviewService.deleteReview(review_id)) {			
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "리뷰를 수정한다, SUCCESS/FAIL", response = String.class)
	@PutMapping("/{review_id}")
	public ResponseEntity<String> updateReviewOne(@RequestBody ReviewDto reviewDto) throws Exception {
		if (reviewService.updateReviewOne(reviewDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}







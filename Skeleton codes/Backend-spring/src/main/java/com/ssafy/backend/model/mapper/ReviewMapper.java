package com.ssafy.backend.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.backend.model.ReviewDto;

@Mapper
public interface ReviewMapper {
	boolean addReview(ReviewDto ReviewDto) throws SQLException; //Create
	List<ReviewDto> getReviews() throws SQLException; //전체 게시물 조회 - 게시판에사용
	ReviewDto getReviewOne(int id) throws SQLException; // 단일 게시물 조회
	boolean deleteReview(int id) throws SQLException; // 게시물 삭제
	boolean updateReviewOne(ReviewDto ReviewDto) throws SQLException; // 게시물 수정
}

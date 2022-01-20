package com.ssafy.backend.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.backend.model.CommentDto;

@Mapper
public interface CommentMapper {
	boolean addComment(CommentDto CommentDto) throws SQLException; //댓글 생성
	List<CommentDto> getComments() throws SQLException; //전체 댓글 조회 - 게시판에사용
	boolean deleteComment(int id) throws SQLException; // 댓글 삭제
	boolean updateCommentOne(CommentDto CommentDto) throws SQLException; // 댓글 수정
}

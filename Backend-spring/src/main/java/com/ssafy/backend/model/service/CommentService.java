package com.ssafy.backend.model.service;
import java.util.List;

import com.ssafy.backend.model.CommentDto;

public interface CommentService {
	
	boolean addComment(CommentDto CommentDto) throws Exception;
	List<CommentDto> getComments() throws Exception; 
	boolean deleteComment(int id) throws Exception;
	boolean updateCommentOne(CommentDto CommentDto) throws Exception;
}
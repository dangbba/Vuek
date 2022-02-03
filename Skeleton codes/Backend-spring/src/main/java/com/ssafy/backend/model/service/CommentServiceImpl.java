package com.ssafy.backend.model.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.backend.model.CommentDto;
import com.ssafy.backend.model.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;
	
	@Override
	public boolean addComment(CommentDto commentDto) throws Exception {
		return commentMapper.addComment(commentDto);
	}
	
	@Override
	public List<CommentDto> getComments() throws Exception {
		return commentMapper.getComments();
	}
	
	@Override
	public boolean updateCommentOne(CommentDto commentDto) throws Exception {
		return commentMapper.updateCommentOne(commentDto);
	}
	@Override
	public boolean deleteComment(int id) throws Exception {
		return commentMapper.deleteComment(id);
	}
}
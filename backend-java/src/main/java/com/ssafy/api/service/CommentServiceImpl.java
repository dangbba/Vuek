package com.ssafy.api.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.db.entity.Comment;
import com.ssafy.db.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public void addComment(@RequestBody Comment comment) throws  Exception {
		commentRepository.saveAndFlush(comment);
	}

	@Override
	public List<Comment> getComments() throws Exception {
		return commentRepository.findAll();
	}
/////////////////////////////
	@Transactional
	@Override
	public void updateCommentOne(Comment comment) throws Exception {
		Comment findComment = getComments().get(comment.getId());
		findComment.setReview(comment.getReview());
		findComment.setComment_content(comment.getComment_content());
		findComment.setUpdated_at(LocalDateTime.now());
		commentRepository.saveAndFlush(findComment);
	}

	@Override
	public void deleteComment(int id) throws Exception {
//		Comment findComment = getComments().get(id);
		commentRepository.deleteById(id);
	}
}
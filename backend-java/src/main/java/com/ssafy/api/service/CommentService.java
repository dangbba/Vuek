package com.ssafy.api.service;


import com.ssafy.db.dto.ConferenceInfoDto;
import com.ssafy.db.dto.EnterWrapperDto;
import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceHistory;
import com.ssafy.db.entity.ConferenceType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CommentService {
	public void addComment(@RequestBody Comment comment) throws Exception;
	public List<Comment> getComments() throws Exception;

	public void updateCommentOne(Comment comment) throws Exception;
	public void deleteComment(int id) throws Exception;
}

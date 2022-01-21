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

import com.ssafy.backend.model.CommentDto;
import com.ssafy.backend.model.service.CommentService;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/comment")
public class CommentController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private CommentService commentService;
	
	@ApiOperation(value = "댓글을 추가하며, 성공/실패에 따라서 SUCCESS/FAIL", response = String.class)
	@PostMapping
	public ResponseEntity<String> addComment(@RequestBody CommentDto commentDto) throws Exception {
		if (commentService.addComment(commentDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "댓글 조회하여 반환", response = String.class)
	@GetMapping("/index")
	public ResponseEntity<List<CommentDto>> getComments() throws Exception {
		return new ResponseEntity<>(commentService.getComments(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "댓글을 삭제한다, SUCCESS/FAIL", response = String.class)
	@DeleteMapping("/{comment_id}")
	public ResponseEntity<String> deleteComment(@PathVariable int comment_id) throws Exception {
		if (commentService.deleteComment(comment_id)) {			
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "댓글을 수정한다, SUCCESS/FAIL", response = String.class)
	@PutMapping("/{comment_id}")
	public ResponseEntity<String> updateCommentOne(@RequestBody CommentDto commentDto) throws Exception {
		if (commentService.updateCommentOne(commentDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}







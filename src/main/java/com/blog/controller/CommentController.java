package com.blog.controller;

import static com.blog.config.AppConstants.COMMENT_DELETE_SUCCESS_MSG;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/users/{userId}/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
			@PathVariable("postId") Integer postId,
			@PathVariable("userId") Integer userId) {

		CommentDto createComment = this.commentService.createComment(commentDto, postId, userId);
		return new ResponseEntity<>(createComment, HttpStatus.CREATED);
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("id") Integer commentId) {

		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(COMMENT_DELETE_SUCCESS_MSG, true), HttpStatus.OK);
	}

}

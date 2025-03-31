package com.nob.pick.post.command.application.service;

import org.springframework.stereotype.Service;

import com.nob.pick.post.command.application.dto.CommentDTO;

@Service
public interface CommandCommentService {
	void registerComment(int postId, CommentDTO newComment);
}

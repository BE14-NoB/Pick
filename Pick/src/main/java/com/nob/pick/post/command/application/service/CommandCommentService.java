package com.nob.pick.post.command.application.service;

import org.springframework.stereotype.Service;

import com.nob.pick.post.command.application.dto.CommentDTO;

@Service
public interface CommandCommentService {
	void registerComment(Long postId, Long rootCommentId, CommentDTO newComment);
	
	String modifyComment(Long commentId, CommentDTO modifiedComment, Long memberId);
}

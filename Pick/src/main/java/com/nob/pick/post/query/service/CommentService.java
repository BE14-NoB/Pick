package com.nob.pick.post.query.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nob.pick.post.command.application.dto.CommentDTO;

@Service
public interface CommentService {
	CommentDTO getCommentById(Long commentId);
	
	List<CommentDTO> getCommentListByPostId(Long id);
}

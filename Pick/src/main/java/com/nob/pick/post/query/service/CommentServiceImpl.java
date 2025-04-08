package com.nob.pick.post.query.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nob.pick.post.command.application.dto.CommentDTO;
import com.nob.pick.post.query.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	
	private final CommentMapper commentMapper;
	
	@Override
	public CommentDTO getCommentById(Long commentId) {
		return commentMapper.selectCommentById(commentId);
	}
	
	@Override
	public List<CommentDTO> getCommentListByPostId(Long id) {
		return commentMapper.selectCommentListByPostId(id);
	}
}

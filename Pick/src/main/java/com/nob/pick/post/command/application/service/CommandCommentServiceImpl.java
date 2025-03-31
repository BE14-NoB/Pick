package com.nob.pick.post.command.application.service;

import org.springframework.stereotype.Service;

import com.nob.pick.post.command.application.dto.CommentDTO;
import com.nob.pick.post.command.domain.aggregate.entity.Comment;
import com.nob.pick.post.command.domain.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommandCommentServiceImpl implements CommandCommentService {
	
	private final CommentRepository commentRepository;
	
	@Override
	public void registerComment(int postId, CommentDTO newComment) {
		Comment comment = commentDTOToComment(newComment);
		commentRepository.save(comment);
	}
	
	private Comment commentDTOToComment(CommentDTO newComment) {
		Comment comment = new Comment();
		
		comment.setId(newComment.getCommentId());
		comment.setIsAdopted(newComment.getCommentIsAdopted());
		comment.setUploadAt(newComment.getCommentUploadAt());
		comment.setUpdateAt(newComment.getCommentUpdateAt());
		comment.setStatus(newComment.getCommentStatus().getValue());
		comment.setPostId(newComment.getCommentPostId());
		comment.setRootCommentId(newComment.getCommentRootCommentId());
		comment.setMemberId(newComment.getCommentMember().getMemberId());
		
		return comment;
	}
}

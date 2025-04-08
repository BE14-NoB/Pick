package com.nob.pick.post.command.application.service;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

import com.nob.pick.post.command.application.dto.CommentDTO;
import com.nob.pick.post.command.application.dto.PostStatus;
import com.nob.pick.post.command.domain.aggregate.entity.Comment;
import com.nob.pick.post.command.domain.repository.CommentRepository;
import com.nob.pick.post.query.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommandCommentServiceImpl implements CommandCommentService {
	
	private final CommentRepository commentRepository;
	private final CommentService commentService;
	
	@Override
	public void registerComment(Long postId, Long rootCommentId, CommentDTO newComment) {
		newComment.setCommentStatus(PostStatus.DEFAULT);
		Comment comment = commentDTOToComment(newComment);
		comment.setPostId(postId);
		comment.setUploadAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
		comment.setRootCommentId(rootCommentId);
		commentRepository.save(comment);
	}
	
	@Override
	public String modifyComment(Long commentId, CommentDTO modifiedComment, Long memberId) {
		if (!memberId.equals(commentService.getCommentById(commentId).getCommentMember().getMemberId())) {
			return "You cannot modify other member's comment";
		}
		modifiedComment.setCommentId(commentId);
		modifiedComment.setCommentUpdateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
		return "Complete";
	}
	
	private Comment commentDTOToComment(CommentDTO newComment) {
		Comment comment = new Comment();
		
		comment.setId(newComment.getCommentId());
		comment.setContent(newComment.getCommentContent());
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

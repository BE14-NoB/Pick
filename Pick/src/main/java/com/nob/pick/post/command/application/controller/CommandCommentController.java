package com.nob.pick.post.command.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.common.util.JwtUtil;
import com.nob.pick.post.command.application.dto.CommentDTO;
import com.nob.pick.post.command.application.dto.MemberNicknameDTO;
import com.nob.pick.post.command.application.service.CommandCommentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/comment")
@Slf4j
@RequiredArgsConstructor
public class CommandCommentController {
	
	private final CommandCommentService commandCommentService;
	private final JwtUtil jwtUtil;
	
	/* 설명. 댓글 등록 */
	/* 설명. 댓글 등록하면 등록 후 자동으로 댓글 단 글을 다시 불러와서 댓글 달린 것을 표시 */
	@PostMapping("/register/{postId}")
	public ResponseEntity<String> registerComment(@PathVariable(name="postId") Long postId, @RequestBody CommentDTO newComment, HttpServletRequest request) {
		
		Long memberId = jwtUtil.getId(request.getHeader("Authorization"));
		newComment.setCommentMember(new MemberNicknameDTO(memberId, null));
		
		Long rootCommentId = null;
		commandCommentService.registerComment(postId, rootCommentId, newComment);
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body("redirect:/post/" + postId);
	}
	
	/* 설명. 대댓글 등록('댓글 등록' 기능과 URN 형식이 달라 기능 분리) */
	@PostMapping("/register/{postId}/{rootCommentId}")
	public ResponseEntity<String> registerReplyComment(@PathVariable(name="postId") Long postId, @PathVariable(name="rootCommentId") Long rootCommentId, @RequestBody CommentDTO newComment, HttpServletRequest request) {
		
		Long memberId = jwtUtil.getId(request.getHeader("Authorization"));
		newComment.setCommentMember(new MemberNicknameDTO(memberId, null));
		commandCommentService.registerComment(postId, rootCommentId, newComment);
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body("redirect:/post/" + postId);
	}
	
	/* 설명. 댓글 수정(내용만 받기) */
	@PostMapping("/modify/{commentId}")
	public ResponseEntity<String> modifyComment(@PathVariable(name="commentId") Long commentId, @RequestBody CommentDTO modifiedComment, HttpServletRequest request) {
		Long memberId = jwtUtil.getId(request.getHeader("Authorization"));
		String message = commandCommentService.modifyComment(commentId, modifiedComment, memberId);
		if (message.equals("Complete")) {
			Long postId = modifiedComment.getCommentPostId();
			return ResponseEntity.ok("redirect:/post/" + postId);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	/* 설명. 댓글 삭제 */
	
	/* 설명. Q&A 댓글 채택 */
}

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
	
	/* 설명. 댓글 등록하면 등록 후 자동으로 댓글 단 글을 다시 불러와서 댓글 달린 것을 표시 */
	@PostMapping("/register/{postId}")
	public ResponseEntity<String> registerComment(@PathVariable int postId, @RequestBody CommentDTO newComment, HttpServletRequest request) {
		
		Long memberId = jwtUtil.getId(request.getHeader("Authorization"));
		newComment.setCommentMember(new MemberNicknameDTO(memberId, null));
		commandCommentService.registerComment(postId, newComment);
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body("redirect:/post/" + postId);
	}
}

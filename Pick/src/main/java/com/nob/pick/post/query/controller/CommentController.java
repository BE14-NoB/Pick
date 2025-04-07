package com.nob.pick.post.query.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.post.command.application.dto.CommentDTO;
import com.nob.pick.post.command.domain.aggregate.vo.ResponseCommentVO;
import com.nob.pick.post.query.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("comment")
@Slf4j
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	
	@GetMapping("/{commentId}")
	public ResponseEntity<ResponseCommentVO> getCommentById(@PathVariable("commentId") Long commentId) {
		CommentDTO commentDTO = commentService.getCommentById(commentId);
		ResponseCommentVO returnValue = commentDTOToResponseCommentVO(commentDTO);
		return ResponseEntity.ok().body(returnValue);
	}
	
	private ResponseCommentVO commentDTOToResponseCommentVO(CommentDTO commentDTO) {
		ResponseCommentVO rcVO = new ResponseCommentVO();
		rcVO.setCommentId(commentDTO.getCommentId());
		rcVO.setCommentIsAdopted(commentDTO.getCommentIsAdopted());
		rcVO.setCommentUploadAt(commentDTO.getCommentUploadAt());
		rcVO.setCommentUpdateAt(commentDTO.getCommentUpdateAt());
		rcVO.setCommentContent(commentDTO.getCommentContent());
		rcVO.setCommentStatus(commentDTO.getCommentStatus().getValue());
		rcVO.setCommentRootCommentId(commentDTO.getCommentRootCommentId());
		rcVO.setCommentMemberId(commentDTO.getCommentMember().getMemberId());
		rcVO.setCommentMemberNickname(commentDTO.getCommentMember().getMemberNickname());
		
		return rcVO;
	}
}

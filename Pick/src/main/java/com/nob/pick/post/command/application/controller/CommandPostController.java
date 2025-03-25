package com.nob.pick.post.command.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.post.command.application.dto.PostDTO;
import com.nob.pick.post.command.application.dto.PostStatus;
import com.nob.pick.post.command.application.service.CommandPostService;
import com.nob.pick.post.command.domain.aggregate.vo.ResponseRegisterPostVO;
import com.nob.pick.post.query.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/post")
@Slf4j
@RequiredArgsConstructor
public class CommandPostController {
	
	private final CommandPostService commandPostService;
	private final PostService postService;
	
	@PostMapping("/register")
	public ResponseEntity<ResponseRegisterPostVO> registerPost(@RequestBody PostDTO newPost) {
		log.info("{}", newPost);
		commandPostService.registerPost(newPost);
		ResponseRegisterPostVO successRegisterPost = postDTOToResponseRegisterPostVO(newPost);
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(successRegisterPost);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<String> deletePost(@RequestParam int postId) {
		PostDTO targetPost = postService.getPostById(postId);
		log.info("targetPost: {}", targetPost);
		if (targetPost == null) {
			return ResponseEntity.ok("Post Not Found");
		}
		if (targetPost.getStatus() == PostStatus.DELETED) {
			return ResponseEntity.ok("Post Is Already Deleted");
		} else if (targetPost.getStatus() == PostStatus.BLINDED) {
			return ResponseEntity.ok("Blinded Post Cannot Be Deleted");
		}
		
		log.info("Deleting post {}", postId);
		commandPostService.deletePost(postId);
		
		targetPost = postService.getPostById(postId);
		log.info("targetPost status: {}", targetPost.getStatus());
		
		if (targetPost.getStatus() == PostStatus.DELETED) {
			return ResponseEntity.ok("Post Delete Success");
		} else {
			return ResponseEntity.ok("Post Delete Failed");
		}
	}
	
	
	private ResponseRegisterPostVO postDTOToResponseRegisterPostVO(PostDTO postDTO) {
		ResponseRegisterPostVO rrpVO = new ResponseRegisterPostVO();
		rrpVO.setId(postDTO.getId());
		rrpVO.setTitle(postDTO.getTitle());
		rrpVO.setContent(postDTO.getContent());
		rrpVO.setCategory(postDTO.getCategory().getValue());
		rrpVO.setUploadAt(postDTO.getUploadAt());
		rrpVO.setMemberNickname(postDTO.getMember().getMemberNickname());
		
		return rrpVO;
	}
}

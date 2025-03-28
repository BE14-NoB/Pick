package com.nob.pick.post.command.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.common.util.JwtUtil;
import com.nob.pick.member.query.dto.MemberDTO;
import com.nob.pick.post.command.application.dto.MemberNicknameDTO;
import com.nob.pick.post.command.application.dto.PostDTO;
import com.nob.pick.post.command.application.service.CommandPostService;
import com.nob.pick.post.command.domain.aggregate.vo.ResponseRegisterPostVO;
import com.nob.pick.infrastructure.MemberServiceClient;

import io.jsonwebtoken.lang.Assert;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/post")
@Slf4j
@RequiredArgsConstructor
public class CommandPostController {
	
	private final CommandPostService commandPostService;
	private final MemberServiceClient msc;
	private final JwtUtil jwtUtil;
	
	@PostMapping("/register")
	public ResponseEntity<ResponseRegisterPostVO> registerPost(@RequestBody PostDTO newPost, HttpServletRequest request) {
		log.info("NewPost: {}", newPost);
		MemberNicknameDTO mnDTO = getMemberNicknameDTO(request);
		newPost.setMember(mnDTO);
		log.info("NewPost Member Added: {}", newPost);
		commandPostService.registerPost(newPost);
		ResponseRegisterPostVO successRegisterPost = postDTOToResponseRegisterPostVO(newPost);
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(successRegisterPost);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<String> deletePost(@RequestParam int postId, HttpServletRequest request) {
		log.info("Deleting post {}", postId);
		int memberId = jwtUtil.getId(request.getHeader("Authorization"));
		return ResponseEntity.ok(commandPostService.deletePost(postId, memberId));
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
	
	private MemberNicknameDTO getMemberNicknameDTO(HttpServletRequest request) {
		MemberDTO member = msc.getMemberById(jwtUtil.getId(request.getHeader("Authorization"))).getBody();
		Assert.notNull(member);
		return new MemberNicknameDTO(member.getId(), member.getNickname());
	}
}

package com.nob.pick.post.command.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.common.util.JwtUtil;
import com.nob.pick.post.command.application.dto.member.MemberDTO;
import com.nob.pick.post.command.application.dto.MemberNicknameDTO;
import com.nob.pick.post.command.application.dto.PostDTO;
import com.nob.pick.post.command.application.service.CommandPostService;
import com.nob.pick.post.command.domain.aggregate.vo.ResponseRegisterPostVO;
import com.nob.pick.post.command.application.infrastructure.PostMemberServiceClient;

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
	private final PostMemberServiceClient msc;
	private final JwtUtil jwtUtil;
	
	/* 설명. 게시글 등록 */
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
	
	/* 설명. 게시글 삭제 */
	@PostMapping("/delete")
	public ResponseEntity<String> deletePost(@RequestParam Long postId, HttpServletRequest request) {
		log.info("Deleting post {}", postId);
		Long memberId = (long)jwtUtil.getId(request.getHeader("Authorization").substring(7).trim());
		return ResponseEntity.ok(commandPostService.deletePost(postId, memberId));
	}
	
	/* 설명. 게시글 수정 */
	
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
		int memberId = jwtUtil.getId(request.getHeader("Authorization").substring(7).trim());
		log.info("Member Id: {}", memberId);
		MemberDTO member = msc.getMemberById(memberId).getBody();
		Assert.notNull(member);
		return new MemberNicknameDTO(member.getId(), member.getNickname());
	}
}

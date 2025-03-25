package com.nob.pick.post.query.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.post.command.application.dto.CommentDTO;
import com.nob.pick.post.command.application.dto.PostCommentDTO;
import com.nob.pick.post.command.application.dto.PostImageDTO;
import com.nob.pick.post.command.application.dto.PostListDTO;
import com.nob.pick.post.command.domain.aggregate.vo.ResponseCommentVO;
import com.nob.pick.post.command.domain.aggregate.vo.ResponsePostCommentVO;
import com.nob.pick.post.command.domain.aggregate.vo.ResponsePostImageVO;
import com.nob.pick.post.command.domain.aggregate.vo.ResponsePostListVO;
import com.nob.pick.post.query.service.PostService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/post")
@Slf4j
public class PostController {
	
	private final PostService postService;
	
	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	/* 설명. 전체 게시글 목록 조회 */
	@GetMapping("/list/{status}")
	public ResponseEntity<List<ResponsePostListVO>> getPostByStatus(@PathVariable String status) {
		// log.info("PostController got status: {}", status);
		List<PostListDTO> postDTOList = postService.getPostListByStatus(status);
		// log.info("PostController After postDTOList: {}", postDTOList);
		List<ResponsePostListVO> returnValue = postListDTOToResponsePostListVO(postDTOList);
		
		return ResponseEntity.ok(returnValue);
	}
	
	/* 설명. 단일 게시글 조회 */
	@GetMapping("/{id}")
	public ResponseEntity<ResponsePostCommentVO> getPostComment(@PathVariable int id) {
		PostCommentDTO postCommentDTO = postService.getPostCommentById(id);
		ResponsePostCommentVO returnValue = postCommentDTOToResponsePostCommentVO(postCommentDTO);
		
		return ResponseEntity.ok(returnValue);
	}
	
	/* 설명. 게시글 목록에서 제목으로 검색 */
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<ResponsePostListVO>> getPostByTitle(@PathVariable String keyword) {
		List<PostListDTO> postDTOList = postService.getPostListByTitle(keyword);
		List<ResponsePostListVO> returnValue = postListDTOToResponsePostListVO(postDTOList);
		return ResponseEntity.ok(returnValue);
	}
	
	/* 설명. 게시글 목록에서 카테고리 별로 조회 */
	@GetMapping("/category/{category}")
	public ResponseEntity<List<ResponsePostListVO>> getPostByCategory(@PathVariable String category) {
		log.info("Controller to Service");
		List<PostListDTO> postDTOList = postService.getPostListByCategory(category);
		log.info("Controller from Service");
		List<ResponsePostListVO> returnValue = postListDTOToResponsePostListVO(postDTOList);
		return ResponseEntity.ok(returnValue);
	}
	
	private ResponsePostCommentVO postCommentDTOToResponsePostCommentVO(PostCommentDTO pcDTO) {
		ResponsePostCommentVO rpcVO = new ResponsePostCommentVO();
		
		rpcVO.setId(pcDTO.getId());
		rpcVO.setTitle(pcDTO.getTitle());
		rpcVO.setContent(pcDTO.getContent());
		rpcVO.setCategory(pcDTO.getCategory());
		rpcVO.setUploadAt(pcDTO.getUploadAt());
		rpcVO.setUpdateAt(pcDTO.getUpdateAt());
		rpcVO.setStatus(pcDTO.getStatus());
		rpcVO.setMemberId(pcDTO.getMember().getMemberId());
		rpcVO.setMemberNickname(pcDTO.getMember().getMemberNickname());
		
		List<ResponsePostImageVO> piVOList = new ArrayList<>();
		for (PostImageDTO piDTO : pcDTO.getPostImageList()) {
			ResponsePostImageVO piVO = new ResponsePostImageVO();
			piVO.setPostImageId(piDTO.getPostImageId());
			piVO.setPostImagePath(piDTO.getPostImagePath());
			piVO.setPostImageRenamedName(piDTO.getPostImageRenamedName());
			piVO.setPostImageIsThumbnail(piDTO.getPostImageIsThumbnail());
			
			piVOList.add(piVO);
		}
		rpcVO.setPostImageVOList(piVOList);
		
		List<ResponseCommentVO> cVOList = new ArrayList<>();
		for (CommentDTO cDTO : pcDTO.getCommentList()) {
			ResponseCommentVO rcVO = new ResponseCommentVO();
			rcVO.setCommentId(cDTO.getCommentId());
			rcVO.setCommentIsAdopted(cDTO.getCommentIsAdopted());
			rcVO.setCommentUploadAt(cDTO.getCommentUploadAt());
			rcVO.setCommentUpdateAt(cDTO.getCommentUpdateAt());
			rcVO.setCommentContent(cDTO.getCommentContent());
			rcVO.setCommentStatus(cDTO.getCommentStatus());
			rcVO.setCommentRootCommentId(cDTO.getCommentRootCommentId());
			rcVO.setCommentMemberId(cDTO.getCommentMember().getMemberId());
			rcVO.setCommentMemberNickname(cDTO.getCommentMember().getMemberNickname());
			
			cVOList.add(rcVO);
		}
		rpcVO.setCommentVOList(cVOList);
		
		return rpcVO;
	}
	
	private List<ResponsePostListVO> postListDTOToResponsePostListVO(List<PostListDTO> postListDTOList) {
		List<ResponsePostListVO> responseList = new ArrayList<>();
		
		for (PostListDTO postListDTO : postListDTOList) {
			ResponsePostListVO responsePost = new ResponsePostListVO();
			responsePost.setId(postListDTO.getId());
			responsePost.setTitle(postListDTO.getTitle());
			responsePost.setCategory(postListDTO.getCategory());
			responsePost.setUploadAt(postListDTO.getUploadAt());
			responsePost.setUpdateAt(postListDTO.getUpdateAt());
			responsePost.setStatus(postListDTO.getStatus());
			responsePost.setMemberId(postListDTO.getMember().getMemberId());
			responsePost.setMemberNickname(postListDTO.getMember().getMemberNickname());
			
			responseList.add(responsePost);
		}
		
		return responseList;
	}
}

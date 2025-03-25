package com.nob.pick.post.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nob.pick.post.command.application.dto.PostCommentDTO;
import com.nob.pick.post.command.application.dto.PostListDTO;
import com.nob.pick.post.query.mapper.PostMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
	private final PostMapper postMapper;
	
	@Autowired
	public PostServiceImpl(PostMapper postMapper) {
		this.postMapper = postMapper;
	}
	
	@Override
	public List<PostListDTO> getPostListByStatus(String status) {
		// log.info("PostService");
		// log.info("Check parseStatus: {}", parseStatus(status));
		List<PostListDTO> postListDTOList = postMapper.selectPostListByStatus(parseStatus(status));
		// log.info("PostService After postListDTOList: {}", postListDTOList.toString());
		return postListDTOList;
	}
	
	@Override
	public PostCommentDTO getPostCommentById(int id) {
		return postMapper.selectPostCommentById(id);
	}
	
	@Override
	public List<PostListDTO> getPostListByTitle(String keyword) {
		return postMapper.selectPostListByTitle(keyword);
	}
	
	@Override
	public List<PostListDTO> getPostListByCategory(String category) {
		log.info("Service from Controller");
		log.info("parsed category: {}", parseCategory(category));
		return postMapper.selectPostListByCategory(parseCategory(category));
	}
	
	private int parseStatus(String status) {	//ENUM으로 바꾸기
		return switch (status) {
			case "default" -> 0;
			case "deleted" -> 1;
			case "blinded" -> 2;
			default -> -1;
		};
	}
	
	private int parseCategory(String category) {
		return switch (category) {
			case "default" -> 1;
			case "Q&A" -> 2;
			default -> -1;
		};
	}
	
	// private List<PostDTO> postToPostDTO(List<Post> postList) {
	// 	List<PostDTO> postDTOList = new ArrayList<>();
	// 	for (Post post : postList) {
	// 		PostDTO postDTO = new PostDTO();
	// 		postDTO.setId(post.getId());
	// 		postDTO.setTitle(post.getTitle());
	// 		postDTO.setContent(post.getContent());
	// 		postDTO.setCategory(post.getCategory());
	// 		postDTO.setUploadAt(post.getUploadAt());
	// 		postDTO.setUpdateAt(post.getUpdateAt());
	// 		postDTO.setStatus(post.getStatus());
	// 		postDTO.setMemberId(post.getMemberId());
	//
	// 		postDTOList.add(postDTO);
	// 	}
	//
	// 	return postDTOList;
	// }
}

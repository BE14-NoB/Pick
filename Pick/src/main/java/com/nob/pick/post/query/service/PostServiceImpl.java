package com.nob.pick.post.query.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nob.pick.post.command.application.dto.CommentDTO;
import com.nob.pick.post.command.application.dto.PostCategory;
import com.nob.pick.post.command.application.dto.PostDTO;
import com.nob.pick.post.command.application.dto.PostImageDTO;
import com.nob.pick.post.command.application.dto.PostListDTO;
import com.nob.pick.post.command.application.dto.PostStatus;
import com.nob.pick.post.query.mapper.PostMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	private final PostMapper postMapper;
	
	@Override
	public List<PostListDTO> getPostListByStatus(String status) {
		log.info("PostService");
		log.info("Check parseStatus: {}", PostStatus.valueOf(status.toUpperCase()).getValue());
		List<PostListDTO> postListDTOList = postMapper.selectPostListByStatus(PostStatus.valueOf(status.toUpperCase()).getValue());
		log.info("PostService After postListDTOList: {}", postListDTOList.toString());
		return postListDTOList;
	}
	
	@Override
	public List<PostListDTO> getPostListByTitle(String keyword) {
		return postMapper.selectPostListByTitle(keyword);
	}
	
	@Override
	public List<PostListDTO> getPostListByCategory(String category) {
		log.info("Service from Controller");
		log.info("parsed category: {}", PostCategory.valueOf(category.toUpperCase()).getValue());
		return postMapper.selectPostListByCategory(PostCategory.valueOf(category.toUpperCase()).getValue());
	}
	
	@Override
	public PostDTO getPostById(Long id) {
		return postMapper.selectPostById(id);
	}
	
	@Override
	public List<PostImageDTO> getPostImageListByPostId(Long id) {
		return postMapper.selectPostImageListByPostId(id);
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

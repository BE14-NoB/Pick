package com.nob.pick.post.query.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nob.pick.post.command.application.dto.PostCommentDTO;
import com.nob.pick.post.command.application.dto.PostListDTO;

@Service
public interface PostService {
	List<PostListDTO> getPostListByStatus(String status);
	
	PostCommentDTO getPostCommentById(int id);
	
	List<PostListDTO> getPostListByTitle(String keyword);
	
	List<PostListDTO> getPostListByCategory(String category);
}

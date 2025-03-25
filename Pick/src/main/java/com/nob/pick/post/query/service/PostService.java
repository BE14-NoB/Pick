package com.nob.pick.post.query.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nob.pick.post.command.application.dto.CommentDTO;
import com.nob.pick.post.command.application.dto.PostDTO;
import com.nob.pick.post.command.application.dto.PostImageDTO;
import com.nob.pick.post.command.application.dto.PostListDTO;

@Service
public interface PostService {
	List<PostListDTO> getPostListByStatus(String status);
	
	List<PostListDTO> getPostListByTitle(String keyword);
	
	List<PostListDTO> getPostListByCategory(String category);
	
	PostDTO getPostById(int id);
	
	List<PostImageDTO> getPostImageListByPostId(int id);
	
	List<CommentDTO> getCommentListByPostId(int id);
}

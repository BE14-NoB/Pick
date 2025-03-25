package com.nob.pick.post.query.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nob.pick.post.command.application.dto.PostCommentDTO;
import com.nob.pick.post.command.application.dto.PostListDTO;

@Mapper
public interface PostMapper {
	List<PostListDTO> selectPostListByStatus(int status);
	
	PostCommentDTO selectPostCommentById(int id);
	
	List<PostListDTO> selectPostListByTitle(String keyword);
	
	List<PostListDTO> selectPostListByCategory(int i);
}

package com.nob.pick.post.query.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nob.pick.post.command.application.dto.CommentDTO;
import com.nob.pick.post.command.application.dto.PostDTO;
import com.nob.pick.post.command.application.dto.PostImageDTO;
import com.nob.pick.post.command.application.dto.PostListDTO;

@Mapper
public interface PostMapper {
	List<PostListDTO> selectPostListByStatus(int status);
	
	List<PostListDTO> selectPostListByTitle(String keyword);
	
	List<PostListDTO> selectPostListByCategory(int i);
	
	PostDTO selectPostById(int id);
	
	List<PostImageDTO> selectPostImageListByPostId(int id);
	
	List<CommentDTO> selectCommentListByPostId(int id);
}

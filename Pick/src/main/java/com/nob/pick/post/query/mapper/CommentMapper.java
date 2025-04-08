package com.nob.pick.post.query.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nob.pick.post.command.application.dto.CommentDTO;

@Mapper
public interface CommentMapper {
	CommentDTO selectCommentById(Long commentId);
	
	List<CommentDTO> selectCommentListByPostId(Long id);
}

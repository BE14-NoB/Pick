package com.nob.pick.post.command.application.service;

import org.springframework.stereotype.Service;

import com.nob.pick.post.command.application.dto.PostDTO;

@Service
public interface CommandPostService {
	void registerPost(PostDTO newPost);
	
	void deletePost(int postId);
}

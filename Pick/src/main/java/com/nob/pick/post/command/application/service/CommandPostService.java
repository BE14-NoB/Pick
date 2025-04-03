package com.nob.pick.post.command.application.service;

import org.springframework.stereotype.Service;

import com.nob.pick.post.command.application.dto.PostDTO;

@Service
public interface CommandPostService {
	void registerPost(PostDTO newPost);
	
	String deletePost(int postId, int memberId);
}

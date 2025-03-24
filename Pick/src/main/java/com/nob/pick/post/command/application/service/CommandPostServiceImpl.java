package com.nob.pick.post.command.application.service;

import org.springframework.stereotype.Service;

import com.nob.pick.post.command.application.dto.PostDTO;
import com.nob.pick.post.command.application.dto.PostStatus;
import com.nob.pick.post.command.domain.aggregate.entity.Post;
import com.nob.pick.post.command.domain.repository.PostRepository;
import com.nob.pick.post.query.service.PostService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommandPostServiceImpl implements CommandPostService {
	
	private final PostRepository postRepository;
	private final PostService postService;
	
	@Override
	@Transactional
	public void registerPost(PostDTO newPost) {
		Post post = postDTOToPost(newPost);
		postRepository.save(post);
		newPost.setId(post.getId());
		newPost.setUploadAt(post.getUploadAt());
		newPost.setMember(postService.getPostById(post.getId()).getMember());
	}
	
	@Override
	@Transactional
	public void deletePost(int postId) {
		Post foundPost = postRepository.findById(postId).get();
		foundPost.setStatus(PostStatus.DELETED.getValue());
	}
	
	private Post postDTOToPost(PostDTO postDTO) {
		Post post = new Post();
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setCategory(postDTO.getCategory().getValue());
		post.setMemberId(postDTO.getMember().getMemberId());
		return post;
	}
}

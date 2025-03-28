package com.nob.pick.post.command.application.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.nob.pick.post.command.application.dto.PostDTO;
import com.nob.pick.post.command.application.dto.PostStatus;
import com.nob.pick.post.command.domain.aggregate.entity.Post;
import com.nob.pick.post.command.domain.repository.PostRepository;
import com.nob.pick.post.query.service.PostService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
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
	public String deletePost(int postId, int memberId) {
		Post targetPost;
		
		// 게시글 id로 게시글 찾기 시도
		try {
			targetPost = postRepository.findById(postId).orElseThrow(NoSuchElementException::new);
		} catch (NoSuchElementException e) {
			targetPost = null;
		}
		
		// 게시글 확인용 로그
		log.info("targetPost: {}", targetPost);
		
		// 게시글 상태에 따른 분기
		if (targetPost == null) {
			// 게시글 없을 시
			return "Post Not Found";
		} else if (targetPost.getMemberId() != memberId) {
			// 게시글 작성자와 현재 회원의 id가 다를 시
			return "You can't delete other one's post";
		} else if (targetPost.getStatus() == PostStatus.DELETED.getValue()) {
			// 게시글이 이미 삭제된 상태라면
			return "Post Is Already Deleted";
		} else if (targetPost.getStatus() == PostStatus.BLINDED.getValue()) {
			// 게시글이 규제된 상태라면
			return "Blinded Post Cannot Be Deleted";
		}
		
		// 위의 조건 모두 통과 시 게시글 삭제처리
		targetPost.setStatus(PostStatus.DELETED.getValue());
		postRepository.save(targetPost);
		
		// 게시글 상태 확인용 로그
		log.info("targetPost status: {}", targetPost.getStatus());
		
		// 게시글 제대로 삭제 되었는지 확인
		if (targetPost.getStatus() == PostStatus.DELETED.getValue()) {
			return "Post Delete Success";
		} else {
			return "Post Delete Failed";
		}
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

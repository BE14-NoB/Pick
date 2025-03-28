package com.nob.pick.post;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nob.pick.post.command.application.dto.PostDTO;
import com.nob.pick.post.command.application.dto.PostStatus;
import com.nob.pick.post.command.application.service.CommandPostService;
import com.nob.pick.post.command.domain.aggregate.entity.Post;
import com.nob.pick.post.command.domain.repository.PostRepository;
import com.nob.pick.post.query.service.PostService;

import io.jsonwebtoken.lang.Assert;

@SpringBootTest
public class DeletePostTest {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommandPostService commandPostService;
	
	@Autowired
	private PostService postService;
	
	@BeforeEach
	void setUp() {
		initPosts();
	}
	
	@AfterEach
	public void clean() {
		initPosts();
	}
	
	private void initPosts() {
		Post defaultPost;
		Post deletedPost;
		Post blindedPost;
		try {
			defaultPost = postRepository.findById(1).orElseThrow();
			deletedPost = postRepository.findById(2).orElseThrow();
			blindedPost = postRepository.findById(3).orElseThrow();
		} catch (NoSuchElementException e) {
			return;
		}
		
		Assert.notNull(defaultPost);
		Assert.notNull(deletedPost);
		Assert.notNull(blindedPost);
		
		defaultPost.setStatus(PostStatus.DEFAULT.getValue());
		postRepository.save(defaultPost);
		
		deletedPost.setStatus(PostStatus.DELETED.getValue());
		postRepository.save(deletedPost);
		
		blindedPost.setStatus(PostStatus.BLINDED.getValue());
		postRepository.save(blindedPost);
	}
	
	@ParameterizedTest
	@DisplayName("게시글 id로 삭제")
	@ValueSource(ints={1, 2, 3, 100})
	public void deletePostTest(int postId) {
		PostDTO targetPost = postService.getPostById(postId);
		String postStatus = (
			targetPost != null ?
			targetPost.getStatus().toString() : "NULL"
		);
		int memberId = (
			targetPost != null ?
			targetPost.getMember().getMemberId() : 0
		);
		Assertions.assertDoesNotThrow(
			() -> {
				System.out.println("Post " + postId + " Origin Status: " + postStatus);
				commandPostService.deletePost(postId, memberId);
			}
		);
	}
}

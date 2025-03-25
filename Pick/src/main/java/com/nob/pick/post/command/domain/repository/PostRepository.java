package com.nob.pick.post.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nob.pick.post.command.domain.aggregate.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}

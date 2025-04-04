package com.nob.pick.badge.command.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nob.pick.badge.command.domain.aggregate.Badge;
import com.nob.pick.challenge.command.domain.aggregate.Challenge;

public interface BadgeRepository extends JpaRepository<Badge, Integer> {
	List<Badge> findByChallenge(Challenge challenge);
}

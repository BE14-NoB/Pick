package com.nob.pick.badge.query.service;

import java.util.List;

import com.nob.pick.badge.query.dto.BadgeQueryDTO;

public interface BadgeQueryService {

	List<BadgeQueryDTO> getAllBadges();

	List<BadgeQueryDTO> getBadgesByChallengeId(int challengeId);
}

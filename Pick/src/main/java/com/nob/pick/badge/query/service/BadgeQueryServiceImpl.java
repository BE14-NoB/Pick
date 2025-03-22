package com.nob.pick.badge.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nob.pick.badge.query.dto.BadgeQueryDTO;
import com.nob.pick.badge.query.mapper.BadgeQueryMapper;

@Service
public class BadgeQueryServiceImpl implements BadgeQueryService {

	private final BadgeQueryMapper badgeQueryMapper;

	@Autowired
	public BadgeQueryServiceImpl(BadgeQueryMapper badgeQueryMapper) {
		this.badgeQueryMapper = badgeQueryMapper;
	}

	@Override
	public List<BadgeQueryDTO> getAllBadges() {
		return badgeQueryMapper.selectAllBadges();
	}

	@Override
	public List<BadgeQueryDTO> getBadgesByChallengeId(int challengeId) {
		return badgeQueryMapper.selectBadgesByChallengeId(challengeId);
	}
}

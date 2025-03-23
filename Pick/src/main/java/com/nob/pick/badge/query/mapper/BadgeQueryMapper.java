package com.nob.pick.badge.query.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nob.pick.badge.query.dto.BadgeQueryDTO;

@Mapper
public interface BadgeQueryMapper {

	List<BadgeQueryDTO> selectAllBadges();

	List<BadgeQueryDTO> selectBadgesByChallengeId(int challengeId);
}

package com.nob.pick.achievement.query.service;

import java.util.List;

import com.nob.pick.achievement.query.dto.MemberAchievementQueryDTO;

public interface MemberAchievementQueryService {

	List<MemberAchievementQueryDTO> getAchievementsByMember(int memberId);

	List<MemberAchievementQueryDTO> getCompletedAchievementsByMember(int memberId);

	List<MemberAchievementQueryDTO> getIncompleteAchievementsByMember(int memberId);
}

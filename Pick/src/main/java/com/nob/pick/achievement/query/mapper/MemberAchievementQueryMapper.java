package com.nob.pick.achievement.query.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nob.pick.achievement.query.dto.MemberAchievementQueryDTO;

@Mapper
public interface MemberAchievementQueryMapper {

	List<MemberAchievementQueryDTO> selectAchievementsByMemberId(int memberId);

	List<MemberAchievementQueryDTO> selectCompletedAchievementsByMemberId(int memberId);

	List<MemberAchievementQueryDTO> selectIncompleteAchievementsByMemberId(int memberId);
}

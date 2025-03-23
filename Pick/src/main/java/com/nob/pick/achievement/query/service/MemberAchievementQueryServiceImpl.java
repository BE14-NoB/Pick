package com.nob.pick.achievement.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nob.pick.achievement.query.dto.MemberAchievementQueryDTO;
import com.nob.pick.achievement.query.mapper.MemberAchievementQueryMapper;

@Service
public class MemberAchievementQueryServiceImpl implements MemberAchievementQueryService {

	private  final MemberAchievementQueryMapper memberAchievementQueryMapper;

	@Autowired
	public MemberAchievementQueryServiceImpl(MemberAchievementQueryMapper memberAchievementQueryMapper) {
		this.memberAchievementQueryMapper = memberAchievementQueryMapper;
	}

	@Override
	public List<MemberAchievementQueryDTO> getAchievementsByMember(int memberId) {
		return memberAchievementQueryMapper.selectAchievementsByMemberId(memberId);
	}

	@Override
	public List<MemberAchievementQueryDTO> getCompletedAchievementsByMember(int memberId) {
		return memberAchievementQueryMapper.selectCompletedAchievementsByMemberId(memberId);
	}

	@Override
	public List<MemberAchievementQueryDTO> getIncompleteAchievementsByMember(int memberId) {
		return memberAchievementQueryMapper.selectIncompleteAchievementsByMemberId(memberId);
	}
}

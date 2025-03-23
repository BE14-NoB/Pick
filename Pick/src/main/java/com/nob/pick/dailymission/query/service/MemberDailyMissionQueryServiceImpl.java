package com.nob.pick.dailymission.query.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nob.pick.dailymission.query.dto.MemberDailyMissionQueryDTO;
import com.nob.pick.dailymission.query.mapper.MemberDailyMissionQueryMapper;

@Service
public class MemberDailyMissionQueryServiceImpl implements MemberDailyMissionQueryService {

	private final MemberDailyMissionQueryMapper memberDailyMissionQueryMapper;

	@Autowired
	public MemberDailyMissionQueryServiceImpl(MemberDailyMissionQueryMapper memberDailyMissionQueryMapper) {
		this.memberDailyMissionQueryMapper = memberDailyMissionQueryMapper;
	}

	@Override
	public List<MemberDailyMissionQueryDTO> getDailyMissionsByMember(int memberId) {
		return memberDailyMissionQueryMapper.selectDailyMissionsByMemberId(memberId);
	}

	@Override
	public List<MemberDailyMissionQueryDTO> getDailyMissionsByMemberWithStatus(int memberId, boolean status) {
		String statusValue = status ? "Y" : "N";
		return memberDailyMissionQueryMapper.selectDailyMissionsByMemberIdAndStatus(memberId, statusValue);
	}
}

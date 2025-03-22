package com.nob.pick.dailymission.query.service;

import java.util.List;

import com.nob.pick.dailymission.query.dto.MemberDailyMissionQueryDTO;

public interface MemberDailyMissionQueryService {
	List<MemberDailyMissionQueryDTO> getDailyMissionsByMember(int memberId);

	List<MemberDailyMissionQueryDTO> getDailyMissionsByMemberWithStatus(int memberId, boolean status);
}

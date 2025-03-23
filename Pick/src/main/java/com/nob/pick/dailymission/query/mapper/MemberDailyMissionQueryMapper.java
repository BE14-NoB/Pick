package com.nob.pick.dailymission.query.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nob.pick.dailymission.query.dto.MemberDailyMissionQueryDTO;

@Mapper
public interface MemberDailyMissionQueryMapper {

	List<MemberDailyMissionQueryDTO> selectDailyMissionsByMemberId(int memberId);

	List<MemberDailyMissionQueryDTO> selectDailyMissionsByMemberIdAndStatus(
		@Param("memberId") int memberId,
		@Param("status") String status);
}

package com.nob.pick.badge.query.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nob.pick.badge.query.dto.MemberBadgeQueryDTO;

@Mapper
public interface MemberBadgeQueryMapper {

	List<MemberBadgeQueryDTO> selectBadgesByMemberId(int memberId);

	int getTotalAdvantageByMemberId(@Param("memberId") int memberId);
}

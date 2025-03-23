package com.nob.pick.badge.query.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nob.pick.badge.query.dto.MemberBadgeQueryDTO;

@Mapper
public interface MemberBadgeQueryMapper {

	List<MemberBadgeQueryDTO> selectBadgesByMemberId(int memberId);
}

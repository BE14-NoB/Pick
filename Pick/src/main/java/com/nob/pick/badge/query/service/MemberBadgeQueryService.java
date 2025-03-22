package com.nob.pick.badge.query.service;

import java.util.List;

import com.nob.pick.badge.query.dto.MemberBadgeQueryDTO;

public interface MemberBadgeQueryService {

	List<MemberBadgeQueryDTO> getBadgesByMember(int memberId);
}

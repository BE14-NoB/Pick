package com.nob.pick.badge.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nob.pick.badge.query.dto.MemberBadgeQueryDTO;
import com.nob.pick.badge.query.mapper.MemberBadgeQueryMapper;

@Service
public class MemberBadgeQueryServiceImpl implements MemberBadgeQueryService {

	private  final MemberBadgeQueryMapper memberBadgeQueryMapper;

	@Autowired
	public MemberBadgeQueryServiceImpl(MemberBadgeQueryMapper memberBadgeQueryMapper) {
		this.memberBadgeQueryMapper = memberBadgeQueryMapper;
	}

	@Override
	public List<MemberBadgeQueryDTO> getBadgesByMember(int memberId) {
		return memberBadgeQueryMapper.selectBadgesByMemberId(memberId);
	}
}

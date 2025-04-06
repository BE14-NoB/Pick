package com.nob.pick.badge.command.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nob.pick.common.dto.MemberResponse;

@FeignClient(name = "pick-member-service")
public interface MemberClient {
	@GetMapping("/api/members/id/{id}")
	MemberResponse getMember(@PathVariable("id") Long id);
}

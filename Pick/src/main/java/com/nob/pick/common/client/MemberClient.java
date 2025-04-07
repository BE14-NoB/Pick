package com.nob.pick.common.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// import com.nob.pick.common.dto.MemberResponse;

@FeignClient(name = "pick-member-service-challenge")  // 추후 'pick-member-service'로 변경
public interface MemberClient {
	// @GetMapping("/api/members/id/{id}")  // 개별 회원 ID 가져오기
	// MemberResponse getMember(@PathVariable("id") Long id);

	@GetMapping("/api/members/ids")  // 모든 회원 ID 가져오기
	List<Long> getAllMemberIds();
}

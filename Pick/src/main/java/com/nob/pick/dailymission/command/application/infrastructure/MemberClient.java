package com.nob.pick.dailymission.command.application.infrastructure;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nob.pick.common.config.infrastructure.FeignClientConfig;
import com.nob.pick.common.dto.MemberResponse;

@FeignClient(name = "pick-member-service-dailymission", configuration= FeignClientConfig.class)
public interface MemberClient {
	@GetMapping("/api/members/id/{id}")  // 개별 회원 ID 가져오기
	MemberResponse getMember(@PathVariable("id") Long id);

	@GetMapping("/api/members/ids")  // 모든 회원 ID 가져오기
	List<Long> getAllMemberIds();
}

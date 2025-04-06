package com.nob.pick.post.command.application.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nob.pick.post.command.application.dto.member.MemberDTO;

@FeignClient(name="pick-member-to-post-service", url="localhost:8000", configuration= FeignClientConfig.class)
public interface PostMemberServiceClient {

    @GetMapping("/api/members/{id}")
    ResponseEntity<MemberDTO> getMemberById(@PathVariable("id") Long memberId);
}

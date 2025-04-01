package com.nob.pick.infrastructure;

import java.util.Map;

import com.nob.pick.matching.query.vo.ResponseMemberProfileVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="pick-member-service", url="localhost:8000", configuration=FeignClientConfig.class)
public interface MemberServiceClient {
    @GetMapping("/pick-member-service/api/members/profile-page/{memberId}")
    ResponseMemberProfileVO getMemberProfileByMemberId(@PathVariable("memberId") int memberId);

    // 추가: 사용자 정보 조회
    @GetMapping("/api/members/user-info")
    Map<String, Object> getUserInfo(@RequestHeader("Authorization") String authorizationHeader);
}

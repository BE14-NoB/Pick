package com.nob.pick.matching.query.infrastructure;

import com.nob.pick.common.config.infrastructure.FeignClientConfig;
import com.nob.pick.matching.query.vo.ResponseMemberProfileVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name="pick-member-service", contextId = "matching2MemberClient", configuration= FeignClientConfig.class)
public interface MatchingMemberServiceClient {
    // 현재 로그인한 회원 정보 가져오기
    @GetMapping("/api/members/user-info")
    Map<String, Object> getUserInfo();

    // 멤버 id로 해당 회원 프로필 조회
    @GetMapping("/api/members/profile-page/{memberId}")
    ResponseMemberProfileVO getMemberProfileByMemberId(@PathVariable("memberId") int memberId);

}

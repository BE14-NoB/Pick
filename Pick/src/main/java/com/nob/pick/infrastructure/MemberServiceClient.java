package com.nob.pick.infrastructure;

import com.nob.pick.matching.query.vo.ResponseMemberProfileVO;
import com.nob.pick.matching.query.vo.ResponseMemberVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="pick-member-service", url="localhost:8000", configuration=FeignClientConfig.class)
public interface MemberServiceClient {
    // 멤버 id로 해당 회원 프로필 조회
    @GetMapping("/pick-member-service/api/members/profile-page/{memberId}")
    ResponseMemberProfileVO getMemberProfileByMemberId(@PathVariable("memberId") long memberId);

    // 토큰으로 해당 회원 조회
    @GetMapping("/pick-member-service/api/members/id")
    ResponseMemberVO getMemberByToken();
}

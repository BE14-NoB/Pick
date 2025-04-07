package com.nob.pick.matching.query.infrastructure;

import com.nob.pick.infrastructure.FeignClientConfig;
import com.nob.pick.matching.query.vo.ResponseMemberProfileVO;
import com.nob.pick.matching.query.vo.ResponseMemberVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="pick-member-service", url="localhost:8000", configuration= FeignClientConfig.class)
public interface MatchingMemberServiceClient {
    // 현재 로그인한 회원 정보 가져오기
    @GetMapping("/api/members/member-Info/id")
    ResponseMemberVO getMemberId();

    // 멤버 id로 해당 회원 프로필 조회
    @GetMapping("/api/members/profile-page/{memberId}")
    ResponseMemberProfileVO getMemberProfileByMemberId(@PathVariable("memberId") int memberId);

}

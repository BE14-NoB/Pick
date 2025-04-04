package com.nob.pick.matching.query.infrastructure;

import com.nob.pick.matching.query.infrastructure.FeignClientConfig;
import com.nob.pick.matching.query.vo.ResponseMemberProfileVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="pick-member-service", url="localhost:8000", configuration= FeignClientConfig.class)
public interface MemberServiceClient {
    // 현재 로그인한 회원 정보 가져오기
    
    // 멤버 id로 해당 회원 프로필 조회
    @GetMapping("/pick-member-service/api/members/profile-page/{memberId}")
    ResponseMemberProfileVO getMemberProfileByMemberId(@PathVariable("memberId") int memberId);
}

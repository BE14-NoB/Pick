package com.nob.pick.badge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.web.servlet.MockMvc;

import com.nob.pick.badge.query.dto.BadgeQueryDTO;
import com.nob.pick.badge.query.dto.MemberBadgeQueryDTO;
import com.nob.pick.badge.query.service.BadgeQueryService;
import com.nob.pick.badge.query.service.MemberBadgeQueryService;

@SpringBootTest
@AutoConfigureMockMvc
public class BadgeTest {

	@Autowired
	private BadgeQueryService badgeQueryService;

	@Autowired
	private MemberBadgeQueryService memberBadgeQueryService;

	private final int TEST_Challenge_ID = 13;
	private final int TEST_Member_ID = 2;
	private final int TEST_Badge_ID = 1;

	@Test
	@DisplayName("뱃지 목록 조회")
	void testGetAllBadges() {
		List<BadgeQueryDTO> badges = badgeQueryService.getAllBadges();
		badges.forEach(System.out::println);
	}

	@Test
	@DisplayName("챌린지별 뱃지 조회")
	void testGetBadgesByChallenge() {
		List<BadgeQueryDTO> badgesByChallenge = badgeQueryService.getBadgesByChallengeId(TEST_Challenge_ID);
		badgesByChallenge.forEach(System.out::println);
	}

	@Test
	@DisplayName("id별 뱃지 정보 조회")
	void testGetBadgeById() {
		BadgeQueryDTO badge = badgeQueryService.getBadgeById(TEST_Badge_ID);
		System.out.println(badge);
	}

	@Test
	@DisplayName("회원별 획득 뱃지 조회")
	void testGetBadgesByMember() {
		List<MemberBadgeQueryDTO> badgesByMember = memberBadgeQueryService.getBadgesByMember(TEST_Member_ID);
		badgesByMember.forEach(System.out::println);
	}

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("뱃지 추가 테스트")
	void testAddBadge() throws Exception {
		// 요청 데이터
		String requestJson = "{\"name\": \"테스트 뱃지\", \"requirement\": 30, \"advantage\": 10, \"description\": \"테스트 뱃지\", \"challengeId\": 2}";

		// 요청 보내기
		mockMvc.perform(post("/badge/add")
				.contentType("application/json")
				.content(requestJson))
			.andExpect(status().isCreated())  // 상태 코드 201 (Created)
			.andExpect(jsonPath("$.name").value("테스트 뱃지"))
			.andExpect(jsonPath("$.requirement").value(30))
			.andExpect(jsonPath("$.advantage").value(10))
			.andExpect(jsonPath("$.description").value("테스트 뱃지"))
			.andExpect(jsonPath("$.challenge.id").value(2))
			.andExpect(jsonPath("$.id").exists());  // 생성된 뱃지에 ID가 존재하는지 확인
	}

	@Test
	@DisplayName("뱃지 수정 테스트")
	void testUpdateBadge() throws Exception {
		// 수정할 뱃지의 ID
		int badgeId = 38;

		// 요청 데이터
		String requestJson = "{\"name\": \"수정된 뱃지\", \"requirement\": 150, \"advantage\": 20, \"description\": \"뱃지 수정\"}";

		// 요청 보내기
		mockMvc.perform(put("/badge/modify/" + badgeId)
				.contentType("application/json")
				.content(requestJson))
			.andExpect(status().isOk())  // 상태 코드 200 (OK)
			.andExpect(jsonPath("$.name").value("수정된 뱃지"))
			.andExpect(jsonPath("$.requirement").value(150))
			.andExpect(jsonPath("$.advantage").value(20))
			.andExpect(jsonPath("$.description").value("뱃지 수정"))
			.andExpect(jsonPath("$.id").value(badgeId));
	}

	@Test
	@DisplayName("뱃지 삭제 테스트")
	void testDeleteBadge() throws Exception {
		// 삭제할 뱃지의 ID
		int badgeId = 38;

		// 요청 보내기
		mockMvc.perform(delete("/badge/delete/" + badgeId))
			.andExpect(status().isNoContent());  // 상태 코드 204 (No Content)
	}

	@Test
	@DisplayName("회원에게 뱃지 부여 테스트")
	void testAwardBadgeToMember() throws Exception {
		// 요청 데이터 준비
		String requestJson = """
        {
            "memberId": 2,
            "achievementId": 2
        }
    """;

		mockMvc.perform(post("/badge/award")
				.contentType("application/json")
				.content(requestJson))
			.andExpect(status().isOk())
			.andExpect(content().string("뱃지가 부여되었습니다."));
	}

	@Test
	@DisplayName("회원별 뱃지 가산점 총합 조회")
	void testGetTotalAdvantageByMemberId() throws Exception {
		int memberId = 10;

		mockMvc.perform(get("/badge/advantage/" + memberId))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$").isNumber());       // 반환값이 숫자인지 확인
	}
}

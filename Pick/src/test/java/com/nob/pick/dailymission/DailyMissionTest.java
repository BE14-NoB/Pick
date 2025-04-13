package com.nob.pick.dailymission;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.nob.pick.common.util.JwtUtil;
import com.nob.pick.dailymission.query.dto.DailyMissionQueryDTO;
import com.nob.pick.dailymission.query.dto.MemberDailyMissionQueryDTO;
import com.nob.pick.dailymission.query.service.DailyMissionQueryService;
import com.nob.pick.dailymission.query.service.MemberDailyMissionQueryService;

@SpringBootTest
@AutoConfigureMockMvc
public class DailyMissionTest {

	@Autowired
	private DailyMissionQueryService dailyMissionQueryService;

	@Autowired
	private MemberDailyMissionQueryService memberDailyMissionQueryService;

	private final int TEST_MEMBER_ID = 1;

	@Test
	@DisplayName("일일 미션 목록 조회")
	void testGetAllDailyMissions() {
		List<DailyMissionQueryDTO> dailymissions = dailyMissionQueryService.getAllDailyMissions();
		dailymissions.forEach(System.out::println);
	}

	@Test
	@DisplayName("회원별 일일 미션 조회")
	void testGetDailyMissionsByMember() {
		List<MemberDailyMissionQueryDTO> memberDailyMissions = memberDailyMissionQueryService.getDailyMissionsByMember(TEST_MEMBER_ID);
		memberDailyMissions.forEach(System.out::println);
	}

	@Test
	@DisplayName("회원별 수행 일일 미션 조회")
	void testGetCompletedDailyMissions() {
		Boolean status = true;
		List<MemberDailyMissionQueryDTO> memberDailyMissions = memberDailyMissionQueryService.getDailyMissionsByMemberWithStatus(TEST_MEMBER_ID, status);
		memberDailyMissions.forEach(System.out::println);
	}

	@Test
	@DisplayName("회원별 미수행 일일 미션 조회")
	void testGetIncompleteDailyMissions() {
		Boolean status = false;
		List<MemberDailyMissionQueryDTO> memberDailyMissions = memberDailyMissionQueryService.getDailyMissionsByMemberWithStatus(TEST_MEMBER_ID, status);
		memberDailyMissions.forEach(System.out::println);
	}

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("일일 미션 추가 테스트")
	void testAddDailyMission() throws Exception {
		// 요청 데이터 준비
		String requestJson = "{\"content\": \"새로운 미션1\", \"expPoint\": 20, \"challengeId\": 2}";

		// 요청 보내기
		mockMvc.perform(post("/daily-mission/add")
				.contentType("application/json")
				.content(requestJson))
			.andExpect(status().isCreated())  // 상태 코드 201 (Created)
			.andExpect(jsonPath("$.content").value("새로운 미션1"))  // 반환된 데이터에서 content 확인
			.andExpect(jsonPath("$.expPoint").value(20))  // 반환된 데이터에서 expPoint 확인
			.andExpect(jsonPath("$.challenge.id").value(2))  // 반환된 데이터에서 challengeId 확인
			.andExpect(jsonPath("$.id").exists());  // 생성된 일일 미션에 ID가 존재하는지 확인
	}


	@Test
	@DisplayName("일일 미션 수정 테스트")
	void testUpdateDailyMission() throws Exception {
		// 수정할 일일 미션의 ID (예시로 10번)
		int dailyMissionId = 10;

		// 요청 데이터 준비
		String requestJson = "{\"content\": \"수정된 일일 미션\", \"expPoint\": 20}";

		// 요청 보내기
		mockMvc.perform(put("/daily-mission/modify/" + dailyMissionId)
				.contentType("application/json")
				.content(requestJson))
			.andExpect(status().isOk())  // 상태 코드 200 (OK)
			.andExpect(jsonPath("$.content").value("수정된 일일 미션"))  // 수정된 content 값 확인
			.andExpect(jsonPath("$.expPoint").value(20))  // 수정된 expPoint 값 확인
			.andExpect(jsonPath("$.id").value(dailyMissionId));  // 수정된 일일 미션 ID 확인
	}

	@Test
	@DisplayName("일일 미션 삭제 테스트")
	void testDeleteDailyMission() throws Exception {
		// 삭제할 일일 미션의 ID (예시로 15번)
		int dailyMissionId = 15;

		// 요청 보내기
		mockMvc.perform(delete("/daily-mission/delete/" + dailyMissionId))
			.andExpect(status().isNoContent());
	}

	@Autowired
	private JwtUtil jwtUtil;

	@Test
	@DisplayName("일일 미션 부여 테스트")
	void testAssignDailyMissions() throws Exception {
		// given
		int memberId = 1;
		String token = jwtUtil.createTokenForTest(memberId); // JwtUtil 내 테스트용 메서드

		// when & then
		mockMvc.perform(post("/daily-mission/assign")
				.header("Authorization", "Bearer " + token))
			.andExpect(status().isOk())
			.andExpect(content().string("일일 미션 부여 완료 또는 이미 부여됨"));
	}

	@Test
	@DisplayName("일일 미션 초기화 테스트")
	void testResetMemberDailyMissions() throws Exception {
		mockMvc.perform(delete("/dailymission/reset"))
			.andExpect(status().isOk())
			.andExpect(content().string("모든 회원 일일 미션이 초기화되었습니다."));
	}

	@Test
	@DisplayName("일일 미션 완료 처리 테스트")
	void completeDailyMission() throws Exception {
		// GIVEN
		int memberId = 1;
		int missionId = 1;
		String token = jwtUtil.createTokenForTest(memberId);

		// WHEN & THEN
		mockMvc.perform(
				put("/daily-mission/{id}/complete", missionId)
					.header("Authorization", "Bearer " + token)
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andExpect(content().string("일일미션을 완료했습니다!"));
	}
}

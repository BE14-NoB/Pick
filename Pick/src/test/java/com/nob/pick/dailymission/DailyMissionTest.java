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
import org.springframework.test.web.servlet.MockMvc;

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

	// @Autowired
	// private DailyMissionAssignmentService dailyMissionAssignmentService;
	//
	// @Autowired
	// private MemberRepository memberRepository;
	//
	// @Autowired
	// private MemberDailyMissionRepository memberDailyMissionRepository;
	//
	// @Autowired
	// private DailyMissionRepository dailyMissionRepository;
	//
	// @Test
	// void testAssignDailyMissionsToAllMembers() {
	// 	// 메소드 호출하여 모든 회원에게 일일 미션 부여
	// 	dailyMissionAssignmentService.assignDailyMissionsToAllMembers();
	//
	// 	// 모든 회원 목록 가져오기
	// 	List<Member> members = memberRepository.findAll();
	//
	// 	// 각 회원에 대해 일일 미션이 부여되었는지 확인
	// 	for (Member member : members) {
	// 		List<MemberDailyMission> memberDailyMissions = memberDailyMissionRepository.findByMemberId(member.getId());
	//
	// 		// 일일 미션이 존재하는지, 그리고 기본값이 'N'인지 확인
	// 		for (MemberDailyMission memberDailyMission : memberDailyMissions) {
	// 			assertEquals(false, memberDailyMission.getIsCompleted());  // 'N'으로 초기화 되어야 함
	// 		}
	// 	}
	// }

	// @Test
	// void testDeleteOldDailyMissions() {
	// 	// 미션을 먼저 생성
	// 	DailyMission dailyMission = new DailyMission();
	// 	dailyMission.setContent("Test mission");
	// 	dailyMission.setExpPoint(10); // 다른 속성 설정
	//
	// 	dailyMissionRepository.save(dailyMission);  // DailyMission 저장
	//
	// 	// 1주일 이상 지난 미션을 일부러 만들어 놓기
	// 	MemberDailyMission oldMission = new MemberDailyMission();
	// 	oldMission.setAcceptedDate(LocalDate.now().minusWeeks(2).toString());  // 2주 전 날짜 설정
	// 	oldMission.setDailyMission(dailyMission); // dailyMission에 값을 할당
	// 	memberDailyMissionRepository.save(oldMission);
	//
	// 	// 삭제 작업 수행
	// 	dailyMissionAssignmentService.deleteOldDailyMissions();
	//
	// 	// 1주일 전 날짜를 LocalDate로 변환
	// 	LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
	//
	// 	// 삭제된 미션이 존재하지 않아야 함
	// 	List<MemberDailyMission> remainingMissions = memberDailyMissionRepository
	// 		.findByAcceptedDateBefore(oneWeekAgo.toString()); // String으로 변환하여 전달
	//
	// 	// 1주일이 지난 미션은 모두 삭제되어야 하므로, 남아 있는 미션이 없어야 함
	// 	assertTrue(remainingMissions.isEmpty(), "1주일 이상 지난 미션이 삭제되지 않았습니다.");
	// }
}

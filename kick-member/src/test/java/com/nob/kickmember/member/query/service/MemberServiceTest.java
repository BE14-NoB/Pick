package com.nob.kickmember.member.query.service;

import com.nob.kickmember.member.query.dto.MemberDTO;
import com.nob.kickmember.member.query.dto.MemberProfilePageDTO;
import com.nob.kickmember.member.query.dto.ProgrammingLanguageInfoDTO;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceTest {

	@Autowired
	private MemberService memberService;

	@Autowired
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		// JdbcTemplate 초기화
		jdbcTemplate = new JdbcTemplate(dataSource);

		// 테이블 초기화  제약조건 문제
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
		jdbcTemplate.update("DELETE FROM member_programming_language");
		jdbcTemplate.update("DELETE FROM programming_language");
		jdbcTemplate.update("DELETE FROM member_profile_page");
		jdbcTemplate.update("DELETE FROM member");
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");

		// 테스트 데이터 삽입
		jdbcTemplate.update(
			"INSERT INTO member (id, name, age, ihidnum, phone_number, email, password, nickname, status, regulated_count, user_grant) VALUES " +
				"(1, '김민수', 25, '950101-1234567', '010-1234-5678', 'minsu1@naver.com', 'Pass1234', '민수킹', 0, 1, 0)"
		);
		jdbcTemplate.update(
			"INSERT INTO member (id, name, age, ihidnum, phone_number, email, password, nickname, status, regulated_count, user_grant) VALUES " +
				"(2, '이영희', 30, '930215-2345678', '010-2345-6789', 'younghee2@gmail.com', 'Young567', '영희짱', 1, 0, 0)"
		);


		jdbcTemplate.update(
			"INSERT INTO member_profile_page (id, exp, level, introduce, preferred_area, git_address, score, image_path, member_id) VALUES " +
				"(1, 1500, 3, '안녕하세요, 김민수입니다.', '서울', 'https://github.com/minsu1', 85, '/images/minsu1.jpg', 1)"
		);
		jdbcTemplate.update(
			"INSERT INTO member_profile_page (id, exp, level, introduce, preferred_area, git_address, score, image_path, member_id) VALUES " +
				"(2, 2000, 4, '코딩 좋아하는 이영희예요.', '부산', 'https://github.com/younghee2', 90, '/images/younghee2.png', 2)"
		);


		jdbcTemplate.update(
			"INSERT INTO programming_language (id, language, is_deleted) VALUES " +
				"(1, 'Java', 'N')"
		);
		jdbcTemplate.update(
			"INSERT INTO programming_language (id, language, is_deleted) VALUES " +
				"(2, 'Python', 'N')"
		);

		jdbcTemplate.update(
			"INSERT INTO member_programming_language (programming_language_id, member_profile_page_id) VALUES " +
				"(1, 1)"
		);
		jdbcTemplate.update(
			"INSERT INTO member_programming_language (programming_language_id, member_profile_page_id) VALUES " +
				"(2, 1)"
		);
	}

	@Test
	@DisplayName("ID로 회원 정보 조회 - 성공 케이스")
	void testFindMemberInfoById_Success() {
		// Given
		int memberId = 1;

		// When
		MemberDTO member = memberService.findMemberInfoById(memberId);

		// Then
		assertNotNull(member);
		assertEquals("김민수", member.getName());
		assertEquals("minsu1@naver.com", member.getEmail());
	}

	@Test
	@DisplayName("ID로 회원 정보 조회 - 실패 케이스")
	void testFindMemberInfoById_Failure() {
		// Given
		int memberId = 999;

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			memberService.findMemberInfoById(memberId);
		});
		assertEquals("ID로 회원을 찾을 수 없습니다.", exception.getMessage());
	}

	@Test
	@DisplayName("이메일 존재 여부 확인 - 존재하는 경우")
	void testCheckEmailExists_Exists() {
		// Given
		String email = "minsu1@naver.com";

		// When
		boolean exists = memberService.checkEmailExists(email);

		// Then
		assertTrue(exists);
	}

	@Test
	@DisplayName("이메일 존재 여부 확인 - 존재하지 않는 경우")
	void testCheckEmailExists_NotExists() {
		// Given
		String email = "notfound@example.com";

		// When
		boolean exists = memberService.checkEmailExists(email);

		// Then
		assertFalse(exists);
	}

	@Test
	@DisplayName("회원 ID로 프로필 페이지 조회 - 성공 케이스")
	void testFindProfilePageByMemberId_Success() {
		// Given
		int memberId = 1;

		// When
		MemberProfilePageDTO profilePage = memberService.findProfilePageByMemberId(memberId);

		// Then
		assertNotNull(profilePage);
		assertEquals(1500, profilePage.getExp());
		assertEquals("안녕하세요, 김민수입니다.", profilePage.getIntroduce());
	}

	@Test
	@DisplayName("회원 ID로 프로필 페이지 조회 - 실패 케이스")
	void testFindProfilePageByMemberId_Failure() {
		// Given
		int memberId = 999;

		// When
		MemberProfilePageDTO profilePage = memberService.findProfilePageByMemberId(memberId);

		// Then
		assertNull(profilePage);
	}

	@Test
	@DisplayName("활성화된 프로그래밍 언어 조회")
	void testFindActiveProgrammingLanguages() {
		// When
		List<ProgrammingLanguageInfoDTO> languages = memberService.findActiveProgrammingLanguages();

		// Then
		assertNotNull(languages);
		assertEquals(2, languages.size()); // Java, Python
		assertTrue(languages.stream().anyMatch(lang -> "Java".equals(lang.getLanguage())));
		assertTrue(languages.stream().anyMatch(lang -> "Python".equals(lang.getLanguage())));
	}
}
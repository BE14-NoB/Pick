package com.nob.pick.member.command.controller;

import com.nob.pick.common.util.JwtUtil;
import com.nob.pick.member.command.dto.SignUpCommandDTO;
import com.nob.pick.member.command.entity.Member;
import com.nob.pick.member.command.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberCommandControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@BeforeEach
	public void setUp() {
		// 테스트 전에 데이터베이스 초기화
		memberRepository.deleteAll();
	}

	@Test
	public void testSignup() throws Exception {
		// Given
		SignUpCommandDTO signUpDTO = new SignUpCommandDTO();
		signUpDTO.setName("고고");
		signUpDTO.setAge(11);
		signUpDTO.setIhidnum("199988-3393933");
		signUpDTO.setPhoneNumber("010-7782-9019");
		signUpDTO.setEmail("lasttest@gmail.com");
		signUpDTO.setPassword("gogogo");
		signUpDTO.setNickname("고고더스트");

		String requestBody = """
                {
                    "name": "고고",
                    "age": 11,
                    "ihidnum": "199988-3393933",
                    "phoneNumber": "010-7782-9019",
                    "email": "lasttest@gmail.com",
                    "password": "gogogo",
                    "nickname": "고고더스트"
                }
                """;

		// When & Then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/members/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string("회원가입 성공"))
			.andDo(print());

		// 데이터베이스에 저장된 비밀번호 확인
		Member member = memberRepository.findByEmail("lasttest@gmail.com").orElseThrow();
		assert passwordEncoder.matches("gogogo", member.getPassword());
	}

	@Test
	public void testLoginSuccess() throws Exception {
		// Given: 회원 가입
		Member member = new Member("고고", 11, "199988-3393933", "010-7782-9019", "lasttest@gmail.com", passwordEncoder.encode("gogogo"), "고고더스트");
		memberRepository.save(member);

		String requestBody = """
                {
                    "email": "lasttest@gmail.com",
                    "password": "gogogo"
                }
                """;

		// When & Then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/members/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
			.andDo(print());
	}

	@Test
	public void testLoginFailureWrongPassword() throws Exception {
		// Given: 회원 가입
		Member member = new Member("고고", 11, "199988-3393933", "010-7782-9019", "lasttest@gmail.com", passwordEncoder.encode("gogogo"), "고고더스트");
		memberRepository.save(member);

		String requestBody = """
                {
                    "email": "lasttest@gmail.com",
                    "password": "wrongpassword"
                }
                """;

		// When & Then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/members/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(MockMvcResultMatchers.status().isUnauthorized())
			.andExpect(MockMvcResultMatchers.content().string("비밀번호가 일치하지 않습니다."))
			.andDo(print());
	}

	@Test
	public void testLoginFailureUserNotFound() throws Exception {
		// Given: 회원이 존재하지 않음
		String requestBody = """
                {
                    "email": "nonexistent@gmail.com",
                    "password": "gogogo"
                }
                """;

		// When & Then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/members/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(MockMvcResultMatchers.status().isUnauthorized())
			.andExpect(MockMvcResultMatchers.content().string("사용자를 찾을 수 없습니다: nonexistent@gmail.com"))
			.andDo(print());
	}
}
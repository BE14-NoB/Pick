package com.nob.pick.member.command.controller;

import com.nob.pick.common.util.JwtUtil;
import com.nob.pick.common.util.TokenBlacklist;
import com.nob.pick.member.command.dto.UpdateMemberCommandDTO;
import com.nob.pick.member.command.dto.LoginDTO;
import com.nob.pick.member.command.dto.SignUpCommandDTO;
import com.nob.pick.member.command.dto.UpdateMemberProfilePageCommandDTO;
import com.nob.pick.member.command.dto.UpdateStatusCommandDTO;
import com.nob.pick.member.command.entity.Member;
import com.nob.pick.member.command.entity.MemberProfilePage;
import com.nob.pick.member.command.repository.MemberRepository;
import com.nob.pick.member.command.service.MemberCommandService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestController
@RequestMapping("/api/members")
public class MemberCommandController {

	private final JwtUtil jwtUtil;
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final TokenBlacklist tokenBlacklist;
	private final MemberCommandService memberCommandService;

	public MemberCommandController(JwtUtil jwtUtil,
		MemberRepository memberRepository,
		BCryptPasswordEncoder passwordEncoder,
		TokenBlacklist tokenBlacklist,
		MemberCommandService memberCommandService) {
		this.jwtUtil = jwtUtil;
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenBlacklist = tokenBlacklist;
		this.memberCommandService = memberCommandService;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody SignUpCommandDTO signUpCommandDTO) {
		// 이메일 중복 확인
		if (memberRepository.findByEmail(signUpCommandDTO.getEmail()).isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Collections.singletonMap("error", "이미 존재하는 이메일입니다: " + signUpCommandDTO.getEmail()));
		}

		// 회원 정보 생성
		Member member = new Member();
		member.setEmail(signUpCommandDTO.getEmail());
		member.setPassword(passwordEncoder.encode(signUpCommandDTO.getPassword())); // 비밀번호 암호화
		member.setName(signUpCommandDTO.getName());
		member.setUserGrant(signUpCommandDTO.getUserGrant() != null ? signUpCommandDTO.getUserGrant() : 0); // 기본값: MEMBER

		// DB에 저장
		memberRepository.save(member);

		return ResponseEntity.ok(Collections.singletonMap("message", "회원가입 성공"));
	}

	@PatchMapping("/edit")
	public ResponseEntity<?> edit(HttpServletRequest request, @RequestBody UpdateMemberCommandDTO updateMemberCommandDTO) {
		// 토큰 추출
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Collections.singletonMap("error", "토큰이 필요합니다."));
		}

		String token = bearerToken.substring(7).trim();
		if (!jwtUtil.validateToken(token) || jwtUtil.isTokenExpired(token) || tokenBlacklist.isBlacklisted(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(Collections.singletonMap("error", "유효하지 않은 토큰입니다."));
		}

		// 현재 사용자 이메일 추출
		String email = jwtUtil.getEmail(token);
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

		// 수정할 정보 업데이트
		if (updateMemberCommandDTO.getPassword() != null && !updateMemberCommandDTO.getPassword().isEmpty()) {
			member.setPassword(passwordEncoder.encode(updateMemberCommandDTO.getPassword())); // 비밀번호 암호화
		}
		if (updateMemberCommandDTO.getName() != null && !updateMemberCommandDTO.getName().isEmpty()) {
			member.setName(updateMemberCommandDTO.getName());
		}

		// DB에 저장
		memberRepository.save(member);

		return ResponseEntity.ok(Collections.singletonMap("message", "회원 정보 수정 성공"));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
		System.out.println("Login request received: " + loginDTO.getEmail());
		Member member = memberRepository.findByEmail(loginDTO.getEmail())
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + loginDTO.getEmail()));

		if (!passwordEncoder.matches(loginDTO.getPassword(), member.getPassword())) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}

		String token = jwtUtil.createToken(loginDTO.getEmail());
		System.out.println("Generated token: " + token);
		return ResponseEntity.ok(Collections.singletonMap("token", token));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Collections.singletonMap("error", "토큰이 필요합니다."));
		}

		String token = bearerToken.substring(7);
		tokenBlacklist.blacklistToken(token);
		return ResponseEntity.ok(Collections.singletonMap("message", "로그아웃 성공"));
	}

	@PatchMapping("/status/{id}")
	public ResponseEntity<?> updateMemberStatus(@PathVariable("id") Long id,
		@RequestBody UpdateStatusCommandDTO updateStatusCommandDTO) {
		if (id <= 0) {
			return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid ID"));
		}
		try {
			memberCommandService.updateMemberStatus(id, updateStatusCommandDTO);
			return ResponseEntity.ok(Collections.singletonMap("message", "Status updated successfully"));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Collections.singletonMap("error", e.getMessage()));
		}
	}

	@PostMapping("/profile/{id}")
	public ResponseEntity<?> updateMemberProfilePage(@PathVariable("id") int id,
		@RequestBody UpdateMemberProfilePageCommandDTO updateMemberProfilePageCommandDTO) {
		if (id <= 0) {
			return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid ID"));
		}

	}
}
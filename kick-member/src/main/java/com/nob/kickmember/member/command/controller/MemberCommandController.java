package com.nob.kickmember.member.command.controller;

import com.nob.kickmember.common.util.JwtUtil;
import com.nob.kickmember.common.util.TokenBlacklist; // TokenBlacklist 유지
import com.nob.kickmember.member.command.dto.AddMemberLanguageDTO;
import com.nob.kickmember.member.command.dto.AddProgrammingLanguageDTO;
import com.nob.kickmember.member.command.dto.UpdateMemberCommandDTO;
import com.nob.kickmember.member.command.dto.LoginDTO;
import com.nob.kickmember.member.command.dto.SignUpCommandDTO;
import com.nob.kickmember.member.command.dto.UpdateMemberLanguageDTO;
import com.nob.kickmember.member.command.dto.UpdateProfileStatusDTO;
import com.nob.kickmember.member.command.dto.UpdateProgrammingLanguageDTO;
import com.nob.kickmember.member.command.dto.UpdateStatusCommandDTO;
import com.nob.kickmember.member.command.entity.Member;
import com.nob.kickmember.member.command.entity.MemberProfilePage;
import com.nob.kickmember.member.command.entity.MemberProgrammingLanguage;
import com.nob.kickmember.member.command.entity.ProgrammingLanguage;
import com.nob.kickmember.member.command.repository.MemberProfilePageRepository;
import com.nob.kickmember.member.command.repository.MemberProgrammingLanguageRepository;
import com.nob.kickmember.member.command.repository.MemberRepository;
import com.nob.kickmember.member.command.repository.ProgrammingLanguageRepository;
import com.nob.kickmember.member.command.service.MemberCommandService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/members")
@Slf4j
public class MemberCommandController {

	private final JwtUtil jwtUtil;
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final TokenBlacklist tokenBlacklist; // TokenBlacklist 유지
	private final MemberCommandService memberCommandService;
	private final ProgrammingLanguageRepository programmingLanguageRepository;
	private final MemberProgrammingLanguageRepository memberProgrammingLanguageRepository;
	private final MemberProfilePageRepository memberProfilePageRepository;

	public MemberCommandController(
		JwtUtil jwtUtil,
		MemberRepository memberRepository,
		BCryptPasswordEncoder passwordEncoder,
		TokenBlacklist tokenBlacklist, // 주입 유지
		MemberCommandService memberCommandService,
		ProgrammingLanguageRepository programmingLanguageRepository,
		MemberProgrammingLanguageRepository memberProgrammingLanguageRepository,
		MemberProfilePageRepository memberProfilePageRepository) {
		this.jwtUtil = jwtUtil;
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenBlacklist = tokenBlacklist;
		this.memberCommandService = memberCommandService;
		this.programmingLanguageRepository = programmingLanguageRepository;
		this.memberProgrammingLanguageRepository = memberProgrammingLanguageRepository;
		this.memberProfilePageRepository = memberProfilePageRepository;
	}

	// 회원 관련 Command
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody SignUpCommandDTO signUpCommandDTO) {
		if (memberRepository.findByEmail(signUpCommandDTO.getEmail()).isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Collections.singletonMap("error", "이미 존재하는 이메일입니다: " + signUpCommandDTO.getEmail()));
		}

		Member member = new Member(
			signUpCommandDTO.getName(),
			signUpCommandDTO.getAge(),
			signUpCommandDTO.getIhidnum(),
			signUpCommandDTO.getPhoneNumber(),
			signUpCommandDTO.getEmail(),
			passwordEncoder.encode(signUpCommandDTO.getPassword()),
			signUpCommandDTO.getNickname()
		);
		member.setUserGrant(signUpCommandDTO.getUserGrant() != null ? signUpCommandDTO.getUserGrant() : 1);

		memberRepository.save(member);
		return ResponseEntity.ok(Collections.singletonMap("message", "회원가입 성공"));
	}

	@PatchMapping("/edit")
	public ResponseEntity<?> edit(HttpServletRequest request,
		@RequestBody UpdateMemberCommandDTO updateMemberCommandDTO) {
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

		String email = jwtUtil.getEmail(token);
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

		if (updateMemberCommandDTO.getPassword() != null && !updateMemberCommandDTO.getPassword().isEmpty()) {
			member.setPassword(passwordEncoder.encode(updateMemberCommandDTO.getPassword()));
		}
		if (updateMemberCommandDTO.getName() != null && !updateMemberCommandDTO.getName().isEmpty()) {
			member.setName(updateMemberCommandDTO.getName());
		}

		memberRepository.save(member);
		return ResponseEntity.ok(Collections.singletonMap("message", "회원 정보 수정 성공"));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
		log.info("Login endpoint hit - URI: {}", "/api/members/login");
		System.out.println("해당 아이디에 대한 로그인 요청 발생: " + loginDTO.getEmail());
		String token = memberCommandService.login(loginDTO);
		System.out.println("생성된 토큰: " + token);
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
		log.debug("Logout request - Token: {}", token);
		tokenBlacklist.blacklistToken(token); // 블랙리스트 추가
		log.info("Token blacklisted successfully: {}", token);
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

	@PatchMapping("profile/status/{id}")
	public ResponseEntity<Void> updateProfileStatus(@PathVariable("id") int id,
		@RequestBody UpdateProfileStatusDTO request) {
		if (id <= 0 || request.getProfilePageId() != id) {
			return ResponseEntity.badRequest().build();
		}
		memberCommandService.updateProfileStatus(request);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/programming-languages")
	public ResponseEntity<?> addProgrammingLanguage(HttpServletRequest request,
		@RequestBody AddProgrammingLanguageDTO addProgrammingLanguageDTO) {
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

		List<String> roles = jwtUtil.getRoles(token);
		if (!roles.contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(Collections.singletonMap("error", "관리자 권한이 필요합니다."));
		}

		ProgrammingLanguage programmingLanguage = new ProgrammingLanguage(addProgrammingLanguageDTO.getLanguage());
		programmingLanguageRepository.save(programmingLanguage);

		return ResponseEntity.ok(
			Collections.singletonMap("message", "프로그래밍 언어 추가 성공: " + addProgrammingLanguageDTO.getLanguage()));
	}

	@PatchMapping("/programming-languages/{id}")
	public ResponseEntity<?> updateProgrammingLanguage(HttpServletRequest request, @PathVariable("id") Long id,
		@RequestBody UpdateProgrammingLanguageDTO updateProgrammingLanguageDTO) {
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

		List<String> roles = jwtUtil.getRoles(token);
		if (!roles.contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(Collections.singletonMap("error", "관리자 권한이 필요합니다."));
		}

		ProgrammingLanguage programmingLanguage = programmingLanguageRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("프로그래밍 언어를 찾을 수 없습니다: " + id));

		if (updateProgrammingLanguageDTO.getLanguage() != null && !updateProgrammingLanguageDTO.getLanguage().isEmpty()) {
			programmingLanguage.setLanguage(updateProgrammingLanguageDTO.getLanguage());
		}

		programmingLanguageRepository.save(programmingLanguage);
		return ResponseEntity.ok(Collections.singletonMap("message", "프로그래밍 언어 수정 성공: " + id));
	}

	@DeleteMapping("/programming-languages/{id}")
	public ResponseEntity<?> deleteProgrammingLanguage(HttpServletRequest request, @PathVariable("id") Long id) {
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

		List<String> roles = jwtUtil.getRoles(token);
		if (!roles.contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(Collections.singletonMap("error", "관리자 권한이 필요합니다."));
		}

		ProgrammingLanguage programmingLanguage = programmingLanguageRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("프로그래밍 언어를 찾을 수 없습니다: " + id));

		programmingLanguage.setIsDeleted("Y");
		programmingLanguageRepository.save(programmingLanguage);
		return ResponseEntity.ok(Collections.singletonMap("message", "프로그래밍 언어 삭제 성공: " + id));
	}

	@PostMapping("/programming-languages/member")
	public ResponseEntity<?> addMemberProgrammingLanguage(HttpServletRequest request,
		@RequestBody AddMemberLanguageDTO addMemberLanguageDTO) {
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

		String email = jwtUtil.getEmail(token);
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

		MemberProfilePage memberProfilePage = memberProfilePageRepository.findById(addMemberLanguageDTO.getMemberProfilePageId())
			.orElseThrow(() -> new IllegalArgumentException(
				"프로필 페이지를 찾을 수 없습니다: " + addMemberLanguageDTO.getMemberProfilePageId()));
		if (!memberProfilePage.getMember().getId().equals(member.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(Collections.singletonMap("error", "본인의 프로필 페이지에만 언어를 추가할 수 있습니다."));
		}

		ProgrammingLanguage programmingLanguage = programmingLanguageRepository.findById(addMemberLanguageDTO.getProgrammingLanguageId())
			.orElseThrow(() -> new IllegalArgumentException(
				"프로그래밍 언어를 찾을 수 없습니다: " + addMemberLanguageDTO.getProgrammingLanguageId()));
		if ("Y".equals(programmingLanguage.getIsDeleted())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Collections.singletonMap("error",
					"삭제된 프로그래밍 언어는 추가할 수 없습니다: " + addMemberLanguageDTO.getProgrammingLanguageId()));
		}

		if (memberProgrammingLanguageRepository.findByMemberProfilePageIdAndProgrammingLanguageId(
				addMemberLanguageDTO.getMemberProfilePageId(), addMemberLanguageDTO.getProgrammingLanguageId())
			.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Collections.singletonMap("error",
					"이미 추가된 프로그래밍 언어입니다: " + addMemberLanguageDTO.getProgrammingLanguageId()));
		}

		MemberProgrammingLanguage memberProgrammingLanguage = new MemberProgrammingLanguage(programmingLanguage, memberProfilePage);
		memberProgrammingLanguageRepository.save(memberProgrammingLanguage);

		return ResponseEntity.ok(
			Collections.singletonMap("message", "회원별 프로그래밍 언어 추가 성공: " + programmingLanguage.getLanguage()));
	}

	@PatchMapping("/programming-languages/member")
	public ResponseEntity<?> updateMemberProgrammingLanguage(HttpServletRequest request,
		@RequestBody UpdateMemberLanguageDTO updateMemberLanguageDTO) {
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

		String email = jwtUtil.getEmail(token);
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

		MemberProfilePage memberProfilePage = memberProfilePageRepository.findById(updateMemberLanguageDTO.getMemberProfilePageId())
			.orElseThrow(() -> new IllegalArgumentException(
				"프로필 페이지를 찾을 수 없습니다: " + updateMemberLanguageDTO.getMemberProfilePageId()));
		if (!memberProfilePage.getMember().getId().equals(member.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(Collections.singletonMap("error", "본인의 프로필 페이지에만 언어를 수정할 수 있습니다."));
		}

		MemberProgrammingLanguage memberProgrammingLanguage = memberProgrammingLanguageRepository
			.findByMemberProfilePageIdAndProgrammingLanguageId(
				updateMemberLanguageDTO.getMemberProfilePageId(),
				updateMemberLanguageDTO.getOldProgrammingLanguageId())
			.orElseThrow(() -> new IllegalArgumentException(
				"회원별 프로그래밍 언어를 찾을 수 없습니다: " + updateMemberLanguageDTO.getOldProgrammingLanguageId()));

		ProgrammingLanguage newProgrammingLanguage = programmingLanguageRepository.findById(updateMemberLanguageDTO.getNewProgrammingLanguageId())
			.orElseThrow(() -> new IllegalArgumentException(
				"프로그래밍 언어를 찾을 수 없습니다: " + updateMemberLanguageDTO.getNewProgrammingLanguageId()));
		if ("Y".equals(newProgrammingLanguage.getIsDeleted())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Collections.singletonMap("error",
					"삭제된 프로그래밍 언어로 변경할 수 없습니다: " + updateMemberLanguageDTO.getNewProgrammingLanguageId()));
		}

		if (memberProgrammingLanguageRepository.findByMemberProfilePageIdAndProgrammingLanguageId(
				updateMemberLanguageDTO.getMemberProfilePageId(), updateMemberLanguageDTO.getNewProgrammingLanguageId())
			.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Collections.singletonMap("error",
					"이미 추가된 프로그래밍 언어입니다: " + updateMemberLanguageDTO.getNewProgrammingLanguageId()));
		}

		memberProgrammingLanguage.setProgrammingLanguage(newProgrammingLanguage);
		memberProgrammingLanguageRepository.save(memberProgrammingLanguage);

		return ResponseEntity.ok(
			Collections.singletonMap("message", "회원별 프로그래밍 언어 수정 성공: " + newProgrammingLanguage.getLanguage()));
	}

	@DeleteMapping("/programming-languages/member/{id}")
	public ResponseEntity<?> deleteMemberProgrammingLanguage(HttpServletRequest request, @PathVariable("id") Long id) {
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

		String email = jwtUtil.getEmail(token);
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

		MemberProgrammingLanguage memberProgrammingLanguage = memberProgrammingLanguageRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("회원별 프로그래밍 언어를 찾을 수 없습니다: " + id));

		if (!memberProgrammingLanguage.getMemberProfilePage().getMember().getId().equals(member.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(Collections.singletonMap("error", "본인의 프로필 페이지에만 언어를 삭제할 수 있습니다."));
		}

		memberProgrammingLanguageRepository.delete(memberProgrammingLanguage);
		return ResponseEntity.ok(Collections.singletonMap("message", "회원별 프로그래밍 언어 삭제 성공: " + id));
	}
}
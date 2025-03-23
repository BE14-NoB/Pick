package com.nob.pick.member.command.controller;

import com.nob.pick.member.command.dto.SignUpCommand;
import com.nob.pick.member.command.dto.UpdateMemberCommand;
import com.nob.pick.member.command.service.MemberCommandService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberCommandController {

	private final MemberCommandService memberCommandService;

	// 회원가입 API
	@PostMapping("/signup")
	public ResponseEntity<Long> signUp(@Valid @RequestBody SignUpCommand command) {
		Long memberId = memberCommandService.signUp(command);
		return ResponseEntity.ok(memberId);
	}


	// 회원정보 수정 API
	@PatchMapping("/edit")
	public ResponseEntity<String> updateMember(
		@AuthenticationPrincipal UserDetails userDetails,
		@Valid @RequestBody UpdateMemberCommand command) {
		String email = userDetails.getUsername(); // 로그인한 사용자의 이메일
		memberCommandService.updateMember(email, command);
		return ResponseEntity.ok("회원정보가 성공적으로 수정되었습니다.");
	}
}
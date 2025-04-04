package com.nob.pick.member.query.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.member.query.dto.EmailCheckRequestDTO;
import com.nob.pick.member.query.dto.EmailCheckResponseDTO;
import com.nob.pick.member.query.dto.PhoneCheckRequestDTO;
import com.nob.pick.member.query.dto.PhoneCheckResponseDTO;
import com.nob.pick.member.query.service.MemberService;
import com.nob.pick.member.query.dto.MemberDTO;
import com.nob.pick.member.query.dto.MemberProfilePageDTO;
import com.nob.pick.member.query.dto.ProgrammingLanguageInfoDTO;
import com.nob.pick.member.query.dto.Status;
import com.nob.pick.member.query.dto.UserGrant;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/members")
@Slf4j
public class MemberController {

	private final MemberService memberService;

	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/email")
	public ResponseEntity<MemberDTO> getEmail(@RequestBody MemberDTO request) {
		if (request.getName().isEmpty() || request.getPhoneNumber().isEmpty()) {
			return ResponseEntity.badRequest().body(new MemberDTO());
		}
		String email = memberService.findEmail(request.getName(), request.getPhoneNumber());
		MemberDTO response = new MemberDTO();
		response.setEmail(email);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/password")
	public ResponseEntity<MemberDTO> getPassword(@RequestBody MemberDTO request) {
		if (request.getName().isEmpty() || request.getPhoneNumber().isEmpty() || request.getEmail().isEmpty()) {
			return ResponseEntity.badRequest().body(new MemberDTO());
		}
		String password = memberService.findPassword(request.getName(), request.getPhoneNumber(), request.getEmail());
		MemberDTO response = new MemberDTO();
		response.setPassword(password);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/member-Infos")
	public ResponseEntity<List<MemberDTO>> getMembers() {
		List<MemberDTO> members = memberService.findMemberInfo();
		return ResponseEntity.ok(members);
	}

	@GetMapping("/member-Info/{id}")
	public ResponseEntity<MemberDTO> getMemberInfo(@PathVariable("id") int id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().body(null);
		}
		MemberDTO member = memberService.findMemberInfoById(id);
		if (member == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(member);
	}

	@PostMapping("/check-email")
	public ResponseEntity<EmailCheckResponseDTO> checkEmail(@RequestBody @Valid EmailCheckRequestDTO request) {
		boolean isDuplicated = memberService.checkEmailExists(request.getEmail());
		if (isDuplicated) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new EmailCheckResponseDTO(true, "이미 존재하는 이메일입니다."));
		} else {
			return ResponseEntity.ok()
				.body(new EmailCheckResponseDTO(false, "사용 가능한 이메일입니다."));
		}
	}

	@PostMapping("/check-phone")
	public ResponseEntity<PhoneCheckResponseDTO> checkPhone(@RequestBody @Valid PhoneCheckRequestDTO request) {
		boolean isDuplicated = memberService.checkPhoneNumberExists(request.getPhoneNumber());
		if (isDuplicated) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new PhoneCheckResponseDTO(true, "이미 존재하는 전화번호입니다."));
		} else {
			return ResponseEntity.ok()
				.body(new PhoneCheckResponseDTO(false, "사용 가능한 전화번호입니다."));
		}
	}

	@GetMapping("/status/{id}")
	public ResponseEntity<Status> getMemberStatus(@PathVariable("id") int id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}
		Status status = memberService.findMemberStatus(id);
		if (status == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(status);
	}

	@GetMapping("/user-grant/{id}")
	public ResponseEntity<MemberDTO> getUserGrant(@PathVariable("id") int id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().body(null);
		}
		UserGrant userGrant = memberService.findUserGrant(id);
		if (userGrant == null) {
			return ResponseEntity.notFound().build();
		}
		MemberDTO response = new MemberDTO();
		response.setUserGrant(userGrant);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/profile-page/{memberId}")
	public ResponseEntity<MemberProfilePageDTO> getProfilePage(@PathVariable("memberId") int memberId) {
		if (memberId <= 0) {
			return ResponseEntity.badRequest().body(null);
		}
		MemberProfilePageDTO profilePage = memberService.findProfilePageByMemberId(memberId);
		if (profilePage == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(profilePage);
	}

	@GetMapping("/profile-pages/{profilePageId}/programming-languages")
	public ResponseEntity<List<ProgrammingLanguageInfoDTO>> getProgrammingLanguagesByProfilePageId(@PathVariable("profilePageId") int profilePageId) {
		List<ProgrammingLanguageInfoDTO> languages;
		if (profilePageId <= 0) {
			languages = Collections.emptyList();
		} else {
			languages = memberService.findProgrammingLanguagesByProfilePageId(profilePageId);
			if (languages == null) {
				languages = Collections.emptyList();
			}
		}
		return ResponseEntity.ok(languages);
	}

	@GetMapping("/programming-languages")
	public ResponseEntity<List<ProgrammingLanguageInfoDTO>> getActiveProgrammingLanguages() {
		List<ProgrammingLanguageInfoDTO> languages = memberService.findActiveProgrammingLanguages();
		if (languages == null) {
			languages = Collections.emptyList();
		}
		return ResponseEntity.ok(languages);
	}

}
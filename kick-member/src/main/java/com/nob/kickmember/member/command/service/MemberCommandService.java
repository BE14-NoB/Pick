package com.nob.kickmember.member.command.service;

import com.nob.kickmember.member.command.dto.AddMemberLanguageDTO;
import com.nob.kickmember.member.command.dto.AddProgrammingLanguageDTO;
import com.nob.kickmember.member.command.dto.UpdateMemberCommandDTO;
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

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberCommandService {
	private final MemberRepository memberRepository;
	private final MemberProfilePageRepository memberProfilePageRepository;
	private final ProgrammingLanguageRepository programmingLanguageRepository;
	private final MemberProgrammingLanguageRepository memberProgrammingLanguageRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public MemberCommandService(
		MemberRepository memberRepository,
		MemberProfilePageRepository memberProfilePageRepository,
		ProgrammingLanguageRepository programmingLanguageRepository,
		MemberProgrammingLanguageRepository memberProgrammingLanguageRepository,
		BCryptPasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.memberProfilePageRepository = memberProfilePageRepository;
		this.programmingLanguageRepository = programmingLanguageRepository;
		this.memberProgrammingLanguageRepository = memberProgrammingLanguageRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// 회원 정보 수정
	@Transactional
	public void updateMember(String email, UpdateMemberCommandDTO dto) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("Member not found"));

		// 사용자가 입력한 필드만 업데이트
		if (dto.getName() != null && !dto.getName().isEmpty()) {
			member.setName(dto.getName());
		}
		if (dto.getAge() != null) {
			member.setAge(dto.getAge());
		}
		if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isEmpty()) {
			member.setPhoneNumber(dto.getPhoneNumber());
		}
		if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
			if (!email.equals(dto.getEmail()) && memberRepository.existsByEmail(dto.getEmail())) {
				throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
			}
			member.setEmail(dto.getEmail());
		}
		if (dto.getNickname() != null && !dto.getNickname().isEmpty()) {
			member.setNickname(dto.getNickname());
		}
		if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
			member.setPassword(passwordEncoder.encode(dto.getPassword()));
		}
		if (dto.getStatus() != null) {
			member.setStatus(dto.getStatus().getValue()); // Status Enum -> Integer 변환
		}

		memberRepository.save(member);
	}

	// 회원 상태 수정
	@Transactional
	public void updateMemberStatus(Long id, UpdateStatusCommandDTO dto) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + id));

		if (dto.getStatus() == null) {
			throw new IllegalArgumentException("Status cannot be null");
		}
		member.setStatus(dto.getStatus().getValue());
		memberRepository.save(member);
	}

	// 회원 프로필 페이지 수정
	@Transactional
	public void updateProfileStatus(UpdateProfileStatusDTO dto) {
		MemberProfilePage profilePage = memberProfilePageRepository.findById((long)dto.getProfilePageId())
			.orElseThrow(
				() -> new IllegalArgumentException("Profile page not found with ID: " + dto.getProfilePageId()));

		if (dto.getIntroduce() != null) {
			profilePage.setIntroduce(dto.getIntroduce());
		}
		if (dto.getPreferredArea() != null) {
			profilePage.setPreferredArea(dto.getPreferredArea());
		}
		if (dto.getGitAddress() != null) {
			profilePage.setGitAddress(dto.getGitAddress());
		}
		if (dto.getImagePath() != null) {
			profilePage.setImagePath(dto.getImagePath());
		}

		memberProfilePageRepository.save(profilePage);
	}

	// 프로그래밍 언어 추가
	@Transactional
	public void addProgrammingLanguage(AddProgrammingLanguageDTO dto) {
		ProgrammingLanguage language = new ProgrammingLanguage(dto.getLanguage());
		programmingLanguageRepository.save(language);
	}

	// 프로그래밍 언어 수정
	@Transactional
	public void updateProgrammingLanguage(UpdateProgrammingLanguageDTO dto) {
		ProgrammingLanguage language = programmingLanguageRepository.findById(dto.getId())
			.orElseThrow(() -> new IllegalArgumentException("Programming language not found with ID: " + dto.getId()));
		if (language.getIsDeleted().equals("Y")) {
			throw new IllegalStateException("Cannot update a deleted programming language");
		}
		language.setLanguage(dto.getLanguage());
		programmingLanguageRepository.save(language);
	}

	// 프로그래밍 언어 soft delete
	@Transactional
	public void deleteProgrammingLanguage(Long id) {
		ProgrammingLanguage language = programmingLanguageRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Programming language not found with ID: " + id));
		language.setIsDeleted("Y");
		programmingLanguageRepository.save(language);
	}

	// 회원별 사용 언어 추가
	@Transactional
	public void addMemberLanguage(AddMemberLanguageDTO dto) {
		MemberProfilePage profilePage = memberProfilePageRepository.findById(dto.getMemberProfilePageId())
			.orElseThrow(() -> new IllegalArgumentException(
				"Member profile page not found with ID: " + dto.getMemberProfilePageId()));
		ProgrammingLanguage language = programmingLanguageRepository.findById(dto.getProgrammingLanguageId())
			.orElseThrow(() -> new IllegalArgumentException(
				"Programming language not found with ID: " + dto.getProgrammingLanguageId()));
		if (language.getIsDeleted().equals("Y")) {
			throw new IllegalStateException("Cannot add a deleted programming language");
		}
		// 중복 체크
		if (memberProgrammingLanguageRepository.findByMemberProfilePageIdAndProgrammingLanguageId(
			dto.getMemberProfilePageId(), dto.getProgrammingLanguageId()).isPresent()) {
			throw new IllegalStateException("This programming language is already added for the member");
		}
		MemberProgrammingLanguage memberLanguage = new MemberProgrammingLanguage(language, profilePage);
		memberProgrammingLanguageRepository.save(memberLanguage);
	}

	// 회원별 사용 언어 수정 (기존 언어 삭제 후 새 언어 추가)
	@Transactional
	public void updateMemberLanguage(UpdateMemberLanguageDTO dto) {
		MemberProfilePage profilePage = memberProfilePageRepository.findById(dto.getMemberProfilePageId())
			.orElseThrow(() -> new IllegalArgumentException(
				"Member profile page not found with ID: " + dto.getMemberProfilePageId()));
		ProgrammingLanguage oldLanguage = programmingLanguageRepository.findById(dto.getOldProgrammingLanguageId())
			.orElseThrow(() -> new IllegalArgumentException(
				"Old programming language not found with ID: " + dto.getOldProgrammingLanguageId()));
		ProgrammingLanguage newLanguage = programmingLanguageRepository.findById(dto.getNewProgrammingLanguageId())
			.orElseThrow(() -> new IllegalArgumentException(
				"New programming language not found with ID: " + dto.getNewProgrammingLanguageId()));
		if (newLanguage.getIsDeleted().equals("Y")) {
			throw new IllegalStateException("Cannot add a deleted programming language");
		}
		// 기존 언어 삭제
		MemberProgrammingLanguage memberLanguage = memberProgrammingLanguageRepository
			.findByMemberProfilePageIdAndProgrammingLanguageId(dto.getMemberProfilePageId(),
				dto.getOldProgrammingLanguageId())
			.orElseThrow(() -> new IllegalArgumentException("Member language mapping not found"));
		memberProgrammingLanguageRepository.delete(memberLanguage);
		// 새 언어 추가
		MemberProgrammingLanguage newMemberLanguage = new MemberProgrammingLanguage(newLanguage, profilePage);
		memberProgrammingLanguageRepository.save(newMemberLanguage);
	}

	// 회원별 사용 언어 삭제
	@Transactional
	public void deleteMemberLanguage(Long memberProfilePageId, Long programmingLanguageId) {
		MemberProgrammingLanguage memberLanguage = memberProgrammingLanguageRepository
			.findByMemberProfilePageIdAndProgrammingLanguageId(memberProfilePageId, programmingLanguageId)
			.orElseThrow(() -> new IllegalArgumentException("Member language mapping not found"));
		memberProgrammingLanguageRepository.delete(memberLanguage);
	}
}
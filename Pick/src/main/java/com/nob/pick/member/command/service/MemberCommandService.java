package com.nob.pick.member.command.service;

import com.nob.pick.member.command.entity.Member;
import com.nob.pick.member.command.dto.SignUpCommand;
import com.nob.pick.member.command.dto.UpdateMemberCommand;
import com.nob.pick.member.command.repository.MemberRepository;
import com.nob.pick.member.command.vo.SignUpVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCommandService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	// 회원가입
	@Transactional
	public Long signUp(SignUpCommand command) {
		// DTO -> VO
		SignUpVO signUpVO = new SignUpVO(
			command.getName(),
			command.getAge(),
			command.getIhidnum(),
			command.getPhoneNumber(),
			command.getEmail(),
			command.getPassword(),
			command.getNickname()
		);

		// 중복 검증
		if (memberRepository.existsByEmail(signUpVO.getEmail())) {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}
		if (memberRepository.existsByPhoneNumber(signUpVO.getPhoneNumber())) {
			throw new IllegalArgumentException("이미 존재하는 전화번호입니다.");
		}
		if (memberRepository.existsByIhidnum(signUpVO.getIhidnum())) {
			throw new IllegalArgumentException("이미 존재하는 주민등록번호입니다.");
		}
		if (memberRepository.existsByNickname(signUpVO.getNickname())) {
			throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
		}

		// VO -> Entity
		Member member = new Member(
			signUpVO.getName(),
			signUpVO.getAge(),
			signUpVO.getIhidnum(),
			signUpVO.getPhoneNumber(),
			signUpVO.getEmail(),
			passwordEncoder.encode(signUpVO.getPassword()),
			signUpVO.getNickname()
		);

		Member savedMember = memberRepository.save(member);
		return savedMember.getId();
	}

	// 회원정보 수정
	@Transactional
	public void updateMember(String email, UpdateMemberCommand command) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

		// 중복 검증
		if (command.getEmail() != null && !command.getEmail().equals(member.getEmail())) {
			if (memberRepository.existsByEmail(command.getEmail())) {
				throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
			}
			member.setEmail(command.getEmail());
		}
		if (command.getPhoneNumber() != null && !command.getPhoneNumber().equals(member.getPhoneNumber())) {
			if (memberRepository.existsByPhoneNumber(command.getPhoneNumber())) {
				throw new IllegalArgumentException("이미 존재하는 전화번호입니다.");
			}
			member.setPhoneNumber(command.getPhoneNumber());
		}
		if (command.getNickname() != null && !command.getNickname().equals(member.getNickname())) {
			if (memberRepository.existsByNickname(command.getNickname())) {
				throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
			}
			member.setNickname(command.getNickname());
		}

		// 사용자가 입력한것만 수정할 예정
		if (command.getName() != null) {
			member.setName(command.getName());
		}
		if (command.getAge() != null) {
			member.setAge(command.getAge());
		}

	}
}
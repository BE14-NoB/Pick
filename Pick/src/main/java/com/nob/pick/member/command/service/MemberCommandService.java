package com.nob.pick.member.command.service;

import com.nob.pick.member.command.dto.UpdateMemberCommandDTO;
import com.nob.pick.member.command.dto.UpdateStatusCommandDTO;
import com.nob.pick.member.command.entity.Member;
import com.nob.pick.member.command.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberCommandService {
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public MemberCommandService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}

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
}
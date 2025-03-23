package com.nob.pick.member.query.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nob.pick.member.query.dto.MemberDTO;
import com.nob.pick.member.query.dto.MemberProfilePageDTO;
import com.nob.pick.member.query.dto.ProgrammingLanguageInfoDTO;
import com.nob.pick.member.query.dto.Status;
import com.nob.pick.member.query.dto.UserGrant;
import com.nob.pick.member.query.mapper.MemberMapper;

@Service
public class MemberService {
	private final MemberMapper memberMapper;

	@Autowired
	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}

	public String findEmail(String name, String phoneNumber) {
		Map<String, Object> params = new HashMap<>();
		params.put("name", name);
		params.put("phoneNumber", phoneNumber);
		return Optional.ofNullable(memberMapper.findEmailByNameAndPhone(params))
			.orElseThrow(() -> new IllegalArgumentException("이름과 전화번호로 회원을 찾을 수 없습니다."));
	}

	public String findPassword(String name, String phoneNumber, String email) {
		Map<String, Object> params = new HashMap<>();
		params.put("name", name);
		params.put("phoneNumber", phoneNumber);
		params.put("email", email);
		return Optional.ofNullable(memberMapper.findPasswordByNamePhoneAndEmail(params))
			.orElseThrow(() -> new IllegalArgumentException("이름과 전화번호, 이메일로 회원을 찾을 수 없습니다."));
	}

	public List<MemberDTO> findMemberInfo() {
		return memberMapper.findAllMembers();
	}

	public MemberDTO findMemberInfoById(int id) {
		return Optional.ofNullable(memberMapper.findMemberById(id))
			.orElseThrow(() -> new IllegalArgumentException("ID로 회원을 찾을 수 없습니다."));
	}

	public boolean existsEmail(String email) {
		return memberMapper.existsByEmail(email);
	}

	public boolean existsPhoneNumber(String phoneNumber) {
		return memberMapper.existsByPhoneNumber(phoneNumber);
	}

	public Status findMemberStatus(int id) {
		return memberMapper.findMemberStatusById(id);
	}

	public UserGrant findUserGrant(int id) {
		MemberDTO member = memberMapper.findUserGrantById(id);
		return member != null ? member.getUserGrant() : null;
	}

	public MemberProfilePageDTO findProfilePageByMemberId(int memberId) {
		return memberMapper.findProfilePageByMemberId(memberId);
	}

	public List<ProgrammingLanguageInfoDTO> findProgrammingLanguagesByProfilePageId(int profilePageId) {
		return memberMapper.findProgrammingLanguagesByProfilePageId(profilePageId);
	}

	public List<ProgrammingLanguageInfoDTO> findActiveProgrammingLanguages() {
		return memberMapper.findActiveProgrammingLanguages();
	}
}
package com.nob.pick.member.query.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nob.pick.member.query.dto.MemberDTO;
import com.nob.pick.member.query.dto.MemberProfilePageDTO;
import com.nob.pick.member.query.dto.ProgrammingLanguageInfoDTO;
import com.nob.pick.member.query.dto.Status;

@Mapper
public interface MemberMapper {
	//회원 관련
	String findEmailByNameAndPhone(Map<String, Object> params);

	String findPasswordByNamePhoneAndEmail(Map<String, Object> params);

	List<MemberDTO> findAllMembers();

	MemberDTO findMemberById(@Param("id") int id);

	Status findMemberStatusById(@Param("id") int id);

	int checkExistsByEmail(@Param("email") String email);

	int checkExistsByPhoneNumber(@Param("phoneNumber") String phoneNumber);

	MemberDTO findUserGrantById(@Param("id") int id);

	//Member Profile Page 관련
	MemberProfilePageDTO findProfilePageByMemberId(@Param("memberId") int memberId);

	//프로그래밍 언어, 회원별 프로그래밍 언어 관련

	List<ProgrammingLanguageInfoDTO> findProgrammingLanguagesByProfilePageId(@Param("profilePageId") int profilePageId);

	List<ProgrammingLanguageInfoDTO> findActiveProgrammingLanguages();
}
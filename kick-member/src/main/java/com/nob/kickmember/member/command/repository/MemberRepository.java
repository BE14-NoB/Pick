package com.nob.kickmember.member.command.repository;

import java.util.Optional;

import com.nob.kickmember.member.command.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
	boolean existsByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);

	boolean existsByIhidnum(String ihidnum);

	boolean existsByNickname(String nickname);

	Optional<Member> findByEmail(String email);
}
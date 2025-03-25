package com.nob.pick.member.command.repository;

import com.nob.pick.member.command.entity.MemberProgrammingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberProgrammingLanguageRepository extends JpaRepository<MemberProgrammingLanguage, Long> {

	List<MemberProgrammingLanguage> findByMemberProfilePageId(Long memberProfilePageId);

	Optional<MemberProgrammingLanguage> findByMemberProfilePageIdAndProgrammingLanguageId(Long memberProfilePageId,
		Long programmingLanguageId);
}
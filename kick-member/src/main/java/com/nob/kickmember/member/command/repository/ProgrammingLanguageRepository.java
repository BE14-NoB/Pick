package com.nob.kickmember.member.command.repository;

import com.nob.kickmember.member.command.entity.ProgrammingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguage, Long> {
}
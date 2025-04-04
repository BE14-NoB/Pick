package com.nob.pick.member.command.repository;

import com.nob.pick.member.command.entity.ProgrammingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguage, Long> {
}
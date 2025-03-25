package com.nob.pick.matching.command.repository;

import com.nob.pick.matching.command.aggregate.MatchingEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingEntryRepository extends JpaRepository<MatchingEntryEntity, Integer> {
}

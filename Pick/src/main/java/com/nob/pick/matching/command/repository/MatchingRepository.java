package com.nob.pick.matching.command.repository;

import com.nob.pick.matching.command.aggregate.MatchingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingRepository extends JpaRepository<MatchingEntity, Integer> {
}

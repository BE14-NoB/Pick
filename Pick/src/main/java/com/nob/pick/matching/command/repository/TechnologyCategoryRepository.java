package com.nob.pick.matching.command.repository;

import com.nob.pick.matching.command.aggregate.TechnologyCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyCategoryRepository extends JpaRepository<TechnologyCategoryEntity, Integer> {
}

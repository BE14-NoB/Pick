package com.nob.pick.project.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nob.pick.project.command.domain.aggregate.entity.ProjectMeetingTemplate;

public interface TemplateRepository extends JpaRepository<ProjectMeetingTemplate, Integer> {
}

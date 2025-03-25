package com.nob.pick.project.query.service;

import com.nob.pick.project.query.dto.ProjectRoomDTO;

import java.util.List;

public interface ProjectRoomService {
    List<ProjectRoomDTO> getAllProjects();

    List<ProjectRoomDTO> getFinishedProjects();

    List<ProjectRoomDTO> getActiveProjects();

    List<ProjectRoomDTO> getDeletedProjects();

    List<ProjectRoomDTO> getmatchingProjects();

    ProjectRoomDTO getFinishedProjectDetail(int projectId);

    List<ProjectRoomDTO> getSearchedProjectsByName(String searchName);

    List<ProjectRoomDTO> getSearchedProjectsByTech(List<Integer> technologyCategoryIds);

    List<ProjectRoomDTO> getSearchedProjectsByTechAndName(int categoryId, String searchName);

    ProjectRoomDTO getActiveProjectDetail(int projectId);

    List<ProjectRoomDTO> getProjectsByMemberId(int memberId);
}

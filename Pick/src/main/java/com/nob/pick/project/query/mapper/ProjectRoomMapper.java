package com.nob.pick.project.query.mapper;

import com.nob.pick.project.query.aggregate.ProjectRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectRoomMapper {
    List<ProjectRoom> selectFinishedProjects();

    List<ProjectRoom> selectActiveProjects();

    List<ProjectRoom> selectDeletedProjects();

    List<ProjectRoom> selectMatchingProjects();

    ProjectRoom selectFinishedProjectDetail(int projectId);

    List<ProjectRoom> selectAllProjects();

    List<ProjectRoom> selectProjectByNameKeyword(String searchName);

    List<ProjectRoom> selectProjectByTechKeyword(List<Integer> technologyCategoryIds);

    List<ProjectRoom> selectProjectByBothKeyword(int categoryId, String searchName);

    ProjectRoom selectActiveProjectDetail(int projectId);

    List<ProjectRoom> selectProjectByMemberId(int memberId);
}

package com.nob.pick.project.query.service;

import com.nob.pick.project.query.aggregate.ProjectRoom;
import com.nob.pick.project.query.mapper.ProjectRoomMapper;
import com.nob.pick.project.query.dto.ProjectRoomDTO;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectRoomServiceImpl implements ProjectRoomService {

    private final ProjectRoomMapper projectRoomMapper;

    @Autowired
    public ProjectRoomServiceImpl(ProjectRoomMapper projectRoomMapper) {
        this.projectRoomMapper = projectRoomMapper;
    }

    @Override
    public List<ProjectRoomDTO> getAllProjects() {
        List<ProjectRoom> projectRoomList = projectRoomMapper.selectAllProjects();

        return projectRoomToDTO(projectRoomList);
    }

    @Override
    public List<ProjectRoomDTO> getFinishedProjects() {
        List<ProjectRoom> projectRoomList = projectRoomMapper.selectFinishedProjects();

        return projectRoomToDTO(projectRoomList);
    }


    @Override
    public List<ProjectRoomDTO> getActiveProjects() {
        List<ProjectRoom> projectRoomList = projectRoomMapper.selectActiveProjects();

        return projectRoomToDTO(projectRoomList);
    }

    @Override
    public List<ProjectRoomDTO> getDeletedProjects() {
        List<ProjectRoom> projectRoomList = projectRoomMapper.selectDeletedProjects();

        return projectRoomToDTO(projectRoomList);
    }

    @Override
    public List<ProjectRoomDTO> getmatchingProjects() {
        List<ProjectRoom> projectRoomList = projectRoomMapper.selectMatchingProjects();

        return projectRoomToDTO(projectRoomList);
    }

    @Override
    public ProjectRoomDTO getFinishedProjectDetail(int projectId) {
        ProjectRoom projectDetail = projectRoomMapper.selectFinishedProjectDetail(projectId);

        if (projectDetail == null) {
            try {
                throw new NotFoundException("프로젝트를 찾을 수 없습니다. projectId=" + projectId);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return projectRoomDetailToDTO(projectDetail);

    }

    @Override
    public List<ProjectRoomDTO> getSearchedProjectsByName(String searchName) {
        List<ProjectRoom> projectRoomList = projectRoomMapper.selectProjectByNameKeyword(searchName);
        return projectRoomToDTO(projectRoomList);
    }

    @Override
    public List<ProjectRoomDTO> getSearchedProjectsByTech(List<Integer> technologyCategoryIds) {
        List<ProjectRoom> projectRoomList = projectRoomMapper.selectProjectByTechKeyword(technologyCategoryIds);
        return projectRoomToDTO(projectRoomList);
    }

    @Override
    public List<ProjectRoomDTO> getSearchedProjectsByTechAndName(int categoryId, String searchName) {
        List<ProjectRoom> projectRoomList = projectRoomMapper.selectProjectByBothKeyword(categoryId, searchName);
        return projectRoomToDTO(projectRoomList);
    }


    @Override
    public ProjectRoomDTO getActiveProjectDetail(int projectId) {
        ProjectRoom projectDetail = projectRoomMapper.selectActiveProjectDetail(projectId);

        if (projectDetail == null) {
            try {
                throw new NotFoundException("프로젝트를 찾을 수 없습니다. projectId=" + projectId);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return projectRoomDetailToDTO(projectDetail);
    }

    @Override
    public List<ProjectRoomDTO> getProjectsByMemberId(int memberId) {
        List<ProjectRoom> projectRoomList = projectRoomMapper.selectProjectByMemberId(memberId);
        return projectRoomToDTO(projectRoomList);
    }


    private ProjectRoomDTO projectRoomDetailToDTO(ProjectRoom projectDetail) {
        ProjectRoomDTO dto = new ProjectRoomDTO();

        dto.setId(projectDetail.getId());
        dto.setName(projectDetail.getName());
        dto.setContent(projectDetail.getContent());
        dto.setStartDate(String.valueOf(projectDetail.getStartDate()));
        dto.setEndDate(String.valueOf(projectDetail.getEndDate()));
        dto.setDurationTime(projectDetail.getDurationTime());
        dto.setProjectUrl(projectDetail.getProjectUrl());
        dto.setIntroduction(projectDetail.getIntroduction());
        dto.setThumbnailImage(projectDetail.getThumbnailImage());
        dto.setMaximumParticipant(projectDetail.getMaximumParticipant());
        dto.setTechnologyCategoryId(projectDetail.getTechnologyCategory().getId());
        dto.setTechnologyCategoryName(projectDetail.getTechnologyCategory().getName());
        dto.setSessionCode(projectDetail.getSessionCode());

        return dto;
    }


    // ProjectRoom -> ProjectRoomDTO
    private List<ProjectRoomDTO> projectRoomToDTO(List<ProjectRoom> projectRoomList) {

        List<ProjectRoomDTO> projectRoomDTOList = new ArrayList<>();

        for(ProjectRoom projectRoom : projectRoomList) {
            ProjectRoomDTO dto = new ProjectRoomDTO();
            dto.setId(projectRoom.getId());
            dto.setName(projectRoom.getName());
            dto.setContent(projectRoom.getContent());
            dto.setStartDate(projectRoom.getStartDate().toString()); // yyyy-MM-dd
            dto.setEndDate(projectRoom.getEndDate().toString());
            dto.setDurationTime(projectRoom.getDurationTime());
            dto.setMaximumParticipant(projectRoom.getMaximumParticipant());
            dto.setTechnologyCategoryId(projectRoom.getTechnologyCategory().getId());
            dto.setTechnologyCategoryName(projectRoom.getTechnologyCategory().getName());
            dto.setThumbnailImage(projectRoom.getThumbnailImage());
            dto.setIntroduction(projectRoom.getIntroduction());
            dto.setThumbnailImage(projectRoom.getThumbnailImage());

            projectRoomDTOList.add(dto);
        }
        return projectRoomDTOList;

    }

}

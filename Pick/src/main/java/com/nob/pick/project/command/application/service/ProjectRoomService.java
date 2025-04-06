package com.nob.pick.project.command.application.service;

import org.springframework.web.multipart.MultipartFile;

import com.nob.pick.project.command.application.dto.ProjectRoomEditDTO;
import com.nob.pick.project.command.application.dto.RequestProjectRoomDTO;

public interface ProjectRoomService {

	void createNonMatchingProject(RequestProjectRoomDTO newProjectRoom);

	void createMatchingProject(RequestProjectRoomDTO newProjectRoom);

	void updateProject(int projectId, ProjectRoomEditDTO projectInfo, MultipartFile thumbnailFile, int memberId);

	void joinProjectRoom(int sessionCode, int memberId);
}

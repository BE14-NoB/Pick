package com.nob.pick.project;

import com.nob.pick.project.query.dto.ProjectRoomDTO;
import com.nob.pick.project.query.service.ProjectRoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProjectRoomTest {
    @Autowired
    private ProjectRoomService projectRoomService;

    @Test
    @DisplayName("완료된 프로젝트 목록 조회")
    public void selectFinishedProjects() {
        List<ProjectRoomDTO> finishedProjects = projectRoomService.getFinishedProjects();
        if(finishedProjects.size() > 0) {
            finishedProjects.forEach(System.out::println);
        }else{
            System.out.println("테스트 결과: No Finished Projects");
        }
    }

    @Test
    @DisplayName("팀원 모집 중인 프로젝트 목록 조회")
    public void selectMatchingProjects() {
        List<ProjectRoomDTO> matchingProjects = projectRoomService.getmatchingProjects();
        if(matchingProjects.size() > 0) {
            matchingProjects.forEach(System.out::println);
        }else{
            System.out.println("테스트 결과: No Mathing Projects");
        }
    }

    @Test
    @DisplayName("진행 중인 프로젝트 목록 조회")
    public void selectActiveProjects() {
        List<ProjectRoomDTO> activeProjects = projectRoomService.getActiveProjects();
        if(activeProjects.size() > 0) {
            activeProjects.forEach(System.out::println);
        }else{
            System.out.println("테스트 결과: No Active Projects");
        }
    }
    
    @Test
    @DisplayName("삭제된 프로젝트 목록 조회")
    public void selectDeletedProjects() {
        List<ProjectRoomDTO> deletedProjects = projectRoomService.getDeletedProjects();
        if(deletedProjects.size() > 0) {
            deletedProjects.forEach(System.out::println);
        }else{
            System.out.println("테스트 결과: No Deleted Projects");
        }
    }


}

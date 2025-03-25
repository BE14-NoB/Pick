package com.nob.pick.project.query.dto;

import com.nob.pick.matching.query.aggregate.TechnologyCategory;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectRoomDTO { // 프로젝트 방 DTO
    private int id;                        // 프로젝트 방 번호
    private String name;                   // 프로젝트 이름
    private String content;                // 프로젝트 소개
    private String durationTime;           // 프로젝트 기간
    private int maximumParticipant;        // 프로젝트 최대 인원
    private String startDate;              // 프로젝트 시작일
    private String endDate;                // 프로젝트 종료일
    private String thumbnailImage;         // 프로젝트 썸네일 이미지
    private String introduction;           // 프로젝트 한 줄 소개
    private String projectUrl;             // 프로젝트 링크
    private Integer sessionCode;

    private int technologyCategoryId;
    private String technologyCategoryName;

}

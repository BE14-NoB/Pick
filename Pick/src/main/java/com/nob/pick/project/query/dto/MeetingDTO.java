package com.nob.pick.project.query.dto;

import com.nob.pick.project.query.aggregate.ProjectMeetingImage;
import com.nob.pick.project.query.dto.enums.MeetingParticipantRole;
import com.nob.pick.project.query.dto.enums.TemplateType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MeetingDTO {
    private int id;
    private String title;
    private String content;
    private String uploadTime;;
    private String updateTime;
    private MeetingParticipantDTO author;       // 작성자
    private int projectRoomId;
    private List<ProjectMeetingImage> images;  // 이미지 리스트
    private Integer meetingTemplateId;
    private List<MeetingParticipantDTO> participants;

    private boolean isAutoSave;     // true이면 자동 저장 , false이면 수동 저장(ctrl+s)
}

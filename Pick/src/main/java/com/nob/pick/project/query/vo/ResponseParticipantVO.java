package com.nob.pick.project.query.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseParticipantVO {
    private int participantId;      // 팀원 번호 (PK)
    private boolean isManager;      // 방장 여부
    private String memberName;      // 이름
    private String memberNickname;  // 닉네임
}

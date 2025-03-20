package com.nob.pick.report.query.dto;

import com.nob.pick.report.query.dto.enums.ReportCategory;
import com.nob.pick.report.query.dto.enums.ReportStatus;
import lombok.Getter;
import lombok.Setter;

// Report 데이터를 전달하기 위한 객체

@Getter
@Setter
public class ReportDTO {
    private int id;
    private java.util.Date reportedAt;
    private ReportStatus status;
    private ReportCategory category;
    private int reportedId;
    private int reportReasonId;
    private int memberId;

    // 고민중인 부분
    private Integer reportedMemberId;

}

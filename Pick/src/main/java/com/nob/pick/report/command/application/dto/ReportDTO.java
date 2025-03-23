package com.nob.pick.report.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReportDTO {
    private ReportCategory category;
    private int reportedId;
    private int reportReasonId;
    private int memberId;
}

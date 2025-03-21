package com.nob.pick.report.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportDTO {
    private int id;
    private java.util.Date reportedAt;
    private ReportStatus status;
    private ReportCategory category;
    private int reportedId;
//    private
}

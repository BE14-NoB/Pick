package com.nob.pick.report.command.domain.aggregate;

import com.nob.pick.report.command.application.dto.ReportCategory;
import com.nob.pick.report.command.application.dto.ReportStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Table(name="report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="reported_at", nullable = false)
    private String reportedAt;

    @Builder.Default
    @Column(name="status", nullable = false)
    private int status = 0;

    @Column(name="category", nullable = false)
    private int category;

    @Column(name="reported_id", nullable = false)
    private int reportedId;

    @Builder.Default
    @Column(name="is_deleted", nullable = false, length = 4)
    private String isDeleted = "N";

    @Column(name="report_reason_id", nullable = false)
    private int reportReasonId;

    @Column(name="member_id", nullable = false)
    private int memberId;


    // soft delete 처리 메서드
    public void markAsDeleted() {
        this.isDeleted = "Y";
    }

    // 상태 처리에 따른 status 변경 메서드
    public void changeStatus(ReportStatus status) {
        this.status = status.getNUM();
    }
}

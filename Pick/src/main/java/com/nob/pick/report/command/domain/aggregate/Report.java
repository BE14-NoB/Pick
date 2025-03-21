package com.nob.pick.report.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="reported_at", nullable = false)
    private String reportedAt;

    @Column(name="status", nullable = false)
    private int status = 0;

    @Column(name="category", nullable = false)
    private int category;

    @Column(name="reported_id", nullable = false)
    private int reportedId;

    @Column(name="is_deleted", nullable = false)
    private String isDeleted = "N";

    @Column(name="report_reason_id", nullable = false)
    private int reportReasonId;

    @Column(name="member_id", nullable = false)
    private int memberId;
}

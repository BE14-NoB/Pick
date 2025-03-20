package com.nob.pick.report.query.dto.enums;

import lombok.Getter;

@Getter
public enum ReportStatus {
    PROCESSING(0, "처리중"),         // 처리중
    WAITING(1, "보류"),             // 보류
    COMPLETED(2,"승인");           // 승인

    private final int NUM;
    private final String DESCRIPTION;

    ReportStatus(int num, String description) {
        NUM = num;
        DESCRIPTION = description;
    }

    // DB TINYINT를 ENUM으로 변환
    public static ReportStatus fromNum(int num) {
        for (ReportStatus status : values()) {
            if (status.NUM == num) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invaild num: " + num);
    }

}

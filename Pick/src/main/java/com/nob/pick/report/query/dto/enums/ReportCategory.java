package com.nob.pick.report.query.dto.enums;

import lombok.Getter;

@Getter
public enum ReportCategory {
    MEMBER(0, "회원"),          // 회원
    POST(1, "게시글"),          // 게시글
    COMMENT(2, "댓글");         // 댓글

    private final int NUM;
    private final String DESCRIPTION;

    ReportCategory(int num, String description) {
        NUM = num;
        DESCRIPTION = description;
    }

    // DB의 TINYINT를 ENUM으로 변환
    public static ReportCategory fromNum(int num) {
        for(ReportCategory category : values()) {
            if(category.NUM == num) {return category;}
        }
        throw new IllegalArgumentException("Invalid num: " + num);
    }
}

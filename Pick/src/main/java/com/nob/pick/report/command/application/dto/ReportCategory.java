package com.nob.pick.report.command.application.dto;

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
}

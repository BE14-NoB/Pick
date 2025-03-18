-- 기존 테이블 삭제
DROP TABLE IF EXISTS REPORT CASCADE;
DROP TABLE IF EXISTS REPORT_REASON CASCADE;
DROP TABLE IF EXISTS REGULATION CASCADE;

-- 테이블 생성
-- REPORT_REASON 테이블
CREATE TABLE IF NOT EXISTS REPORT_REASON
(
    id          INT  AUTO_INCREMENT         COMMENT '신고 사유 번호'
,   reason      TEXT NOT NULL UNIQUE        COMMENT '신고 사유명'
,   CONSTRAINT pk_id PRIMARY KEY (id)
);

-- REPORT 테이블
CREATE TABLE IF NOT EXISTS REPORT
(
    id                  INT AUTO_INCREMENT                      COMMENT '신고 번호'
,   reported_at         VARCHAR(255) NOT NULL                   COMMENT '신고 신청일시'
,   status              TINYINT NOT NULL DEFAULT 0              COMMENT '처리 상태 (ENUM("처리중", "보류", "승인"))'
,   category            TINYINT NOT NULL                        COMMENT '신고 유형 (ENUM("회원", "게시글", "댓글"))'
,   reported_id         INTEGER NOT NULL                        COMMENT '신고 대상 id'
,   report_reason_id    INTEGER NOT NULL                        COMMENT '신고 사유 번호'
,   member_id           INTEGER NOT NULL                        COMMENT '신고 신청자'
,   CONSTRAINT pk_id PRIMARY KEY (id)
,   CONSTRAINT fk_report_report_reason_id FOREIGN KEY (report_reason_id) REFERENCES REPORT_REASON (id)
,   CONSTRAINT fk_report_member_id FOREIGN KEY (member_id) REFERENCES MEMBER (id)
);

-- REGULATION 테이블
CREATE TABLE IF NOT EXISTS REGULATION
(
    id              INT AUTO_INCREMENT           COMMENT '규제 번호'
,   start_date      VARCHAR(255) NOT NULL        COMMENT '시작 날짜'
,   end_date        VARCHAR(255) NOT NULL        COMMENT '종료 날짜'
,   member_id       INTEGER NOT NULL             COMMENT '규제 대상 회원'
,   CONSTRAINT pk_id PRIMARY KEY (id)
,   CONSTRAINT fk_regulation_member_id FOREIGN KEY (member_id) REFERENCES MEMBER (id)
);

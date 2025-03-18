DROP TABLE IF EXISTS MEMBER CASCADE;
DROP TABLE IF EXISTS MEMBER_PROFILE_PAGE CASCADE;
DROP TABLE IF EXISTS MEMBER_PROGRAMMING_LANGUAGE CASCADE;
DROP TABLE IF EXISTS PROGRAMMING_LANGUAGE CASCADE;

CREATE TABLE IF NOT EXISTS MEMBER
(
    id             INTEGER      NOT NULL AUTO_INCREMENT
,   name           VARCHAR(255) NOT NULL
,   age            INTEGER      NOT NULL
,   ihidnum        VARCHAR(255) NOT NULL UNIQUE
,   phone_number   VARCHAR(255) NOT NULL UNIQUE
,   email          VARCHAR(255) NOT NULL UNIQUE
,   password       VARCHAR(255) NOT NULL
,   nickname       VARCHAR(255) NOT NULL UNIQUE
,   status         TINYINT      NOT NULL COMMENT '활성화, 비활성화(정지), 탈퇴 한번에 관리 (Enum)'
,   reported_count INTEGER      NOT NULL COMMENT '조회 덜하기 위해 회원 테이블 속성 추가'
,   user_grant     TINYINT      NOT NULL COMMENT 'ADMIN, MEMBER'
,   CONSTRAINT pk_member_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS MEMBER_PROFILE_PAGE
(
    id               INTEGER      NOT NULL AUTO_INCREMENT
,   exp              INTEGER      NOT NULL
,   level            INTEGER      NOT NULL
,   introduce        TEXT         NULL
,   preferred_area   VARCHAR(255) NULL
,   git_address      VARCHAR(255) NULL UNIQUE
,   score            INTEGER      NULL
,   image_path       VARCHAR(255) NULL UNIQUE
,   member_id        INTEGER      NOT NULL
,   CONSTRAINT pk_member_profile_page_id PRIMARY KEY (id)
,   CONSTRAINT fk_member_profile_page_member_id FOREIGN KEY (member_id) REFERENCES MEMBER (id)
);

CREATE TABLE IF NOT EXISTS PROGRAMMING_LANGUAGE
(
id         INTEGER      NOT NULL AUTO_INCREMENT
,   language   VARCHAR(255) NOT NULL UNIQUE
,   is_deleted VARCHAR(4)   NOT NULL DEFAULT 'N'
,   CONSTRAINT pk_programming_language_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS MEMBER_PROGRAMMING_LANGUAGE
(
id                      INTEGER NOT NULL AUTO_INCREMENT
,   programming_language_id INTEGER NOT NULL
,   member_profile_page_id  INTEGER NOT NULL
,   CONSTRAINT pk_member_programming_language_id PRIMARY KEY (id)
,   CONSTRAINT fk_member_programming_language_programming_language_id FOREIGN KEY (programming_language_id) REFERENCES PROGRAMMING_LANGUAGE (id)
,   CONSTRAINT fk_member_programming_member_profile_page_id FOREIGN KEY (member_profile_page_id) REFERENCES MEMBER_PROFILE_PAGE (id)
);

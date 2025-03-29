## PICK - 전체 DDL

-- 기존 테이블 삭제
DROP TABLE IF EXISTS MEMBER CASCADE;
DROP TABLE IF EXISTS MEMBER_PROFILE_PAGE CASCADE;
DROP TABLE IF EXISTS MEMBER_PROGRAMMING_LANGUAGE CASCADE;
DROP TABLE IF EXISTS PROGRAMMING_LANGUAGE CASCADE;

DROP TABLE IF EXISTS MATCHING_ENTRY CASCADE;
DROP TABLE IF EXISTS MATCHING CASCADE;
DROP TABLE IF EXISTS TECHNOLOGY_CATEGORY CASCADE;

DROP TABLE IF EXISTS REPORT CASCADE;
DROP TABLE IF EXISTS REPORT_REASON CASCADE;
DROP TABLE IF EXISTS REGULATION CASCADE;

DROP TABLE IF EXISTS MEMBER_BADGE;
DROP TABLE IF EXISTS MEMBER_DAILY_MISSION;
DROP TABLE IF EXISTS MEMBER_ACHIEVEMENT;
DROP TABLE IF EXISTS BADGE;
DROP TABLE IF EXISTS DAILY_MISSION;
DROP TABLE IF EXISTS ACHIEVEMENT;
DROP TABLE IF EXISTS CHALLENGE;

-- 기존 테이블 삭제

DROP TABLE IF EXISTS PROJECT_MEETING_USED_TEMPLATE CASCADE;	
DROP TABLE IF EXISTS PROJECT_MEETING_TEMPLATE CASCADE;	
DROP TABLE IF EXISTS PROJECT_MEETING_IMAGE CASCADE;	-- 프로젝트 회의록 사진
DROP TABLE IF EXISTS PROJECT_MEETING CASCADE;			-- 프로젝트 회의록
DROP TABLE IF EXISTS PROJECT_REVIEW CASCADE;				-- 프로젝트 후기
DROP TABLE IF EXISTS MEMBER_REVIEW CASCADE;				-- 회원 후기
DROP TABLE IF EXISTS PARTICIPANT CASCADE;					-- 프로젝트 팀원
DROP TABLE IF EXISTS PROJECT_ROOM CASCADE;				-- 프로젝트 방

DROP TABLE IF EXISTS POST_IMAGE CASCADE;
DROP TABLE IF EXISTS COMMENT CASCADE;
DROP TABLE IF EXISTS POST CASCADE;



-- 회원 테이블 생성
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



CREATE TABLE IF NOT EXISTS TECHNOLOGY_CATEGORY
(
    id                         INTEGER      NOT NULL AUTO_INCREMENT
    , name                       VARCHAR(255) NOT NULL
    , is_deleted                 VARCHAR(4)   NOT NULL DEFAULT 'N'
    , ref_technology_category_id INTEGER      NULL
    , CONSTRAINT pk_id PRIMARY KEY (id)
    , CONSTRAINT fk_ref_technology_category_id FOREIGN KEY (ref_technology_category_id) REFERENCES TECHNOLOGY_CATEGORY (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS MATCHING
(
    id                     INTEGER      NOT NULL AUTO_INCREMENT
    , created_date_at        VARCHAR(255) NOT NULL
    , is_completed           VARCHAR(4)   NOT NULL DEFAULT 'N'
    , is_deleted               VARCHAR(4) NOT NULL DEFAULT 'N'
    , maximum_participant INTEGER NOT NULL DEFAULT 5
    , current_participant INTEGER NOT NULL DEFAULT 1
    , level_range            INTEGER      NOT NULL DEFAULT 5
    , member_id              INTEGER      NOT NULL
    , technology_category_id INTEGER      NOT NULL
    , CONSTRAINT pk_id PRIMARY KEY (id)
    , CONSTRAINT fk_matching_member_id FOREIGN KEY (member_id) REFERENCES MEMBER (id)
    , CONSTRAINT fk_matching_technology_category_id FOREIGN KEY (technology_category_id) REFERENCES TECHNOLOGY_CATEGORY (id)
);

CREATE TABLE IF NOT EXISTS MATCHING_ENTRY
(
    id           INTEGER      NOT NULL AUTO_INCREMENT
    , applied_date_at VARCHAR(255) NOT NULL
    , is_canceled   VARCHAR(4)   NOT NULL DEFAULT 'N'
    , is_accepted VARCHAR(4) NOT NULL DEFAULT 'N'
    , member_id    INTEGER      NOT NULL
    , matching_id  INTEGER      NOT NULL
    , CONSTRAINT pk_id PRIMARY KEY (id)
    , CONSTRAINT fk_matching_entry_member_id FOREIGN KEY (member_id) REFERENCES MEMBER(id)
    , CONSTRAINT fk_matching_entry_matching_id FOREIGN KEY (matching_id) REFERENCES MATCHING(id)
);



-- 챌린지 테이블
CREATE TABLE IF NOT EXISTS CHALLENGE
(
    id   INTEGER      NOT NULL AUTO_INCREMENT
    ,   name VARCHAR(255) NOT NULL
    ,   CONSTRAINT pk_challenge_id PRIMARY KEY (id)
    ,   CONSTRAINT uni_challenge_name UNIQUE (name)
);

-- 도전 과제 테이블
CREATE TABLE IF NOT EXISTS ACHIEVEMENT
(
    id           INTEGER    NOT NULL AUTO_INCREMENT
    ,   requirement  INTEGER    NOT NULL
    ,   exp          INTEGER    NOT NULL
    ,   is_deleted   VARCHAR(4) NOT NULL DEFAULT 'N'
    ,   challenge_id INTEGER    NOT NULL
    ,   CONSTRAINT pk_achievement_id PRIMARY KEY (id)
    ,   CONSTRAINT fk_achievement_challenge_id FOREIGN KEY (challenge_id) REFERENCES challenge(id)
);

-- 일일 미션 테이블
CREATE TABLE IF NOT EXISTS DAILY_MISSION
(
    id           INTEGER      NOT NULL AUTO_INCREMENT
    ,   content      TEXT         NOT NULL
    ,   exp_point    INTEGER      NOT NULL
    ,   is_deleted   VARCHAR(4)   NOT NULL DEFAULT 'N'
    ,   challenge_id INTEGER      NOT NULL
    ,   CONSTRAINT pk_daily_mission_id PRIMARY KEY (id)
    ,   CONSTRAINT fk_daily_mission_challenge_id FOREIGN KEY (challenge_id) REFERENCES challenge(id)
);

-- 뱃지 테이블
CREATE TABLE IF NOT EXISTS BADGE
(
    id           INTEGER    NOT NULL AUTO_INCREMENT
    ,   requirement  INTEGER
    ,   advantage    INTEGER    NOT NULL
    ,   description  TEXT       NOT NULL
    ,   is_deleted   VARCHAR(4) NOT NULL DEFAULT 'N'
    ,   challenge_id INTEGER    NOT NULL
    ,   CONSTRAINT pk_badge_id PRIMARY KEY (id)
    ,   CONSTRAINT fk_badge_challenge_id FOREIGN KEY (challenge_id) REFERENCES challenge(id)
);

-- 회원별 도전 과제 테이블
CREATE TABLE IF NOT EXISTS MEMBER_ACHIEVEMENT
(
    id             INTEGER       NOT NULL AUTO_INCREMENT
    ,   achieved_date  VARCHAR(255)
    ,   progress       INTEGER
    ,   achieved_time  INTEGER
    ,   achievement_id INTEGER       NOT NULL
    ,   member_id      INTEGER       NOT NULL
    ,   CONSTRAINT pk_member_achievement_id PRIMARY KEY (id)
    ,   CONSTRAINT fk_member_achievement_achievement_id FOREIGN KEY (achievement_id) REFERENCES achievement(id)
    ,   CONSTRAINT fk_member_achievement_member_id FOREIGN KEY (member_id) REFERENCES member(id)
    ,   CONSTRAINT uni_member_achievement UNIQUE (member_id, achievement_id)
);

-- 회원별 일일 미션 테이블
CREATE TABLE IF NOT EXISTS MEMBER_DAILY_MISSION
(
    id               INTEGER       NOT NULL AUTO_INCREMENT
    ,   is_completed     VARCHAR(4)    NOT NULL DEFAULT 'N'
    ,   accepted_date   VARCHAR(255)
    ,   daily_mission_id INTEGER       NOT NULL
    ,   member_id        INTEGER       NOT NULL
    ,   CONSTRAINT pk_member_daily_mission_id PRIMARY KEY (id)
    ,   CONSTRAINT fk_member_daily_mission_daily_mission_id FOREIGN KEY (daily_mission_id) REFERENCES daily_mission(id)
    ,   CONSTRAINT fk_member_daily_mission_member_id FOREIGN KEY (member_id) REFERENCES member(id)
);

-- 회원별 뱃지 테이블
CREATE TABLE IF NOT EXISTS MEMBER_BADGE
(
    id            INTEGER      NOT NULL AUTO_INCREMENT
    ,   acquired_date VARCHAR(255) NOT NULL
    ,   level         INTEGER      NOT NULL
    ,   badge_id      INTEGER      NOT NULL
    ,   member_id     INTEGER      NOT NULL
    ,   CONSTRAINT pk_member_badge_id PRIMARY KEY (id)
    ,   CONSTRAINT fk_member_badge_badge_id FOREIGN KEY (badge_id) REFERENCES badge(id)
    ,   CONSTRAINT fk_member_badge_member_id FOREIGN KEY (member_id) REFERENCES member(id)
    ,   CONSTRAINT uni_member_badge UNIQUE (member_id, badge_id)
);


-- 프로젝트 관련 DDL -------------------------------------------------------------------------

-- 프로젝트 방 테이블
CREATE TABLE if NOT EXISTS project_room
(
    id	                    INTEGER NOT NULL AUTO_INCREMENT     COMMENT '프로젝트 방 번호'
    , name                      VARCHAR(255) NOT NULL			    COMMENT '프로젝트 이름'
    , content TEXT	            NOT NULL							COMMENT '프로젝트 소개'
    , is_finished               VARCHAR(4) NOT NULL DEFAULT 'N'	    COMMENT '프로젝트 완료 여부'
    , is_deleted                VARCHAR(4) NOT NULL DEFAULT 'N'     COMMENT '프로젝트 삭제 여부'
    , duration_time             VARCHAR(100) NOT NULL			    COMMENT '프로젝트 기간'
    , maximum_participant       INTEGER NOT NULL				    COMMENT '프로젝트 최대 인원'
    , session_code              INTEGER NULL UNIQUE					COMMENT '(자율매칭)프로젝트 입장 코드'
    , start_date                VARCHAR(255) NOT NULL			    COMMENT '프로젝트 시작일'
    , end_date                  VARCHAR(255) NOT NULL 				COMMENT '프로젝트 종료일'
    , technology_category_id    INTEGER NOT NULL 			        COMMENT '프로젝트 기술 분류'
    , thumbnail_image           VARCHAR(255) UNIQUE                 COMMENT '프로젝트 썸네일 이미지'
    , introduction              TEXT                                COMMENT '프로젝트 한줄 소개'
    , project_url               VARCHAR(255) UNIQUE                 COMMENT '프로젝트 링크'
    , CONSTRAINT pk_id PRIMARY KEY (id)
    , CONSTRAINT fk_project_technology_category_id FOREIGN KEY (technology_category_id) REFERENCES TECHNOLOGY_CATEGORY (id)
    , CHECK (maximum_participant > 0)
) ENGINE=INNODB COMMENT '프로젝트 방';


-- 팀원 테이블
CREATE TABLE if NOT EXISTS PARTICIPANT
(
    id                INTEGER NOT NULL AUTO_INCREMENT     COMMENT '팀원 번호'
    , is_manager        VARCHAR(4) NOT NULL DEFAULT 'N'     COMMENT '방장 여부'
    , project_room_id   INTEGER NOT NULL					COMMENT '프로젝트 방 번호'
    , member_id         INTEGER NOT NULL					COMMENT '회원 번호'
    , CONSTRAINT pk_id PRIMARY KEY (id)
    , CONSTRAINT fk_participant_project_room_id FOREIGN KEY (project_room_id) REFERENCES PROJECT_ROOM (id)
    , CONSTRAINT fk_participant_member_id FOREIGN KEY (member_id) REFERENCES MEMBER (id)
    , UNIQUE (project_room_id, member_id)
);


-- 팀원 후기 테이블
CREATE TABLE if NOT EXISTS MEMBER_REVIEW
(
    id	        INTEGER NOT NULL AUTO_INCREMENT             		COMMENT '팀원 후기 번호'
    , rate          INTEGER NOT NULL                                    COMMENT '팀원 후기 평점(1~5)'
    , content       TEXT 									            COMMENT '팀원 후기 내용'
    , reviewer_id   INTEGER NOT NULL	 							    COMMENT '후기 작성자'
    , reviewee_id   INTEGER NOT NULL 								    COMMENT '후기 대상자'
    , CONSTRAINT pk_id PRIMARY KEY (id)
    , CONSTRAINT fk_member_review_reviewer_id FOREIGN KEY (reviewer_id) REFERENCES PARTICIPANT (id)
    , CONSTRAINT fk_member_review_reviewee_id FOREIGN KEY (reviewee_id) REFERENCES PARTICIPANT (id)
    , CONSTRAINT chk_range_of_rate CHECK (rate >= 1 AND rate <= 5)
);



-- 프로젝트 후기 테이블
CREATE TABLE if NOT EXISTS PROJECT_REVIEW
(
    id	            INTEGER NOT NULL AUTO_INCREMENT         COMMENT '프로젝트 후기 번호'
    , reviewer_id       INTEGER NOT NULL						COMMENT '후기 작성자'
    , content TEXT	    NOT NULL								COMMENT '후기 내용'
    , project_room_id   INTEGER NOT NULL
    , CONSTRAINT pk_id PRIMARY KEY (id)
    , CONSTRAINT fk_project_review_project_room_id  FOREIGN KEY (project_room_id) REFERENCES PROJECT_ROOM (id)
    , CONSTRAINT fk_preojct_review_reviewer_id  FOREIGN KEY (reviewer_id) REFERENCES PARTICIPANT (id)
);


-- 프로젝트 회의록
CREATE TABLE if NOT EXISTS PROJECT_MEETING
(
    id                INTEGER NOT NULL AUTO_INCREMENT     COMMENT '회의록 번호'
    , project_room_id   INTEGER NOT NULL					COMMENT '프로젝트 방 번호'
    , title             VARCHAR(255) NOT NULL				COMMENT '회의록 제목'
    , content TEXT	    NOT NULL							COMMENT '회의록 내용'
    , author_id	        INTEGER NOT NULL				    COMMENT '회의록 작성자'
    , upload_time       VARCHAR(255) NOT NULL				COMMENT '작성일'
    , update_time	    VARCHAR(255) 				        COMMENT '수정일'
    , CONSTRAINT pk_id PRIMARY KEY (id)
    , CONSTRAINT fk_project_meeting_member FOREIGN KEY(author_id) REFERENCES MEMBER(id)
    , CONSTRAINT fk_proejct_meeting_project_room_id FOREIGN KEY (project_room_id) REFERENCES PROJECT_ROOM (id)
);

-- 회의록별 참여자 테이블
CREATE TABLE PROJECT_MEETING_PARTICIPANT
(
    id INT NOT NULL AUTO_INCREMENT
    , meeting_id INT NOT NULL
    , participant_id INT NOT NULL
    , role TINYINT(1) NOT NULL DEFAULT 1
    , CONSTRAINT pk_project_meeting_participant PRIMARY KEY (id)
    , FOREIGN KEY (meeting_id) REFERENCES PROJECT_MEETING(id)
    , FOREIGN KEY (participant_id) REFERENCES PARTICIPANT(id)
);

-- 프로젝트 회의록 사진
CREATE TABLE if NOT EXISTS project_meeting_image
(
    id                INTEGER NOT NULL AUTO_INCREMENT     COMMENT '회의록 사진 번호'
    , image_path        VARCHAR(255) NOT NULL 				COMMENT '사진 경로'
    , image_name        VARCHAR(255) NOT NULL				COMMENT '재정의 사진명'
    , is_thumbnail      VARCHAR(4) NOT NULL DEFAULT 'N'     COMMENT '썸네일 여부'
    , meeting_id        INTEGER NOT NULL					COMMENT '회의록 번호'
    , CONSTRAINT pk_id PRIMARY KEY (id)
    , CONSTRAINT fk_project_meeting_image_meeting_id FOREIGN KEY (meeting_id) REFERENCES PROJECT_MEETING (id)
);

-- 회의록 템플릿 테이블
CREATE TABLE IF NOT EXISTS PROJECT_MEETING_TEMPALTE
(
    id INTEGER NOT NULL AUTO_INCREMENT
    , name VARCHAR(255) NOT NULL
    , description VARCHAR(255)
    , type TINYINT(1)
    , content LONGTEXT NOT NULL
    , is_default VARCHAR(4) NOT NULL DEFAULT 'N'
    , CONSTRAINT pk_project_meeting_template PRIMARY KEY (id)
);

-- 회의록별 템플릿 테이블 
CREATE TABLE IF NOT EXISTS PROJECT_MEETING_USED_TEMPLATE
(
    id int NOT NULL AUTO_INCREMENT
    , meeting_id int NOT NULL
    , template_id int NOT NULL
    , CONSTRAINT pk_meeting_used_template_id PRIMARY KEY (id)
    , CONSTRAINT fk_meeting_id FOREIGN KEY (meeting_id) REFERENCES PROJECT_MEETING(id)
    , CONSTRAINT  fk_template_id FOREIGN KEY (template_id) REFERENCES PROJECT_MEETING_TEMPALTE(id)
);


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
    ,   is_deleted          VARCHAR(4) NOT NULL DEFAULT 'N'         COMMENT '신고 삭제 여부'
    ,   report_reason_id    INTEGER NOT NULL                        COMMENT '신고 사유 번호'
    ,   member_id           INTEGER NOT NULL                        COMMENT '신고 신청자'
    ,   CONSTRAINT pk_id PRIMARY KEY (id)
    ,   CONSTRAINT fk_report_report_reason_id FOREIGN KEY (report_reason_id) REFERENCES REPORT_REASON (id)
    ,   CONSTRAINT fk_report_member_id FOREIGN KEY (member_id) REFERENCES MEMBER (id)
);

-- REGULATION 테이블
CREATE TABLE IF NOT EXISTS REGULATION
(
    id              INT AUTO_INCREMENT                      COMMENT '규제 번호'
    ,   start_date      VARCHAR(255) NOT NULL                   COMMENT '시작 날짜'
    ,   end_date        VARCHAR(255) NOT NULL                   COMMENT '종료 날짜'
    ,   is_deleted      VARCHAR(4) NOT NULL DEFAULT 'N'         COMMENT '신고 삭제 여부'
    ,   member_id       INTEGER NOT NULL                        COMMENT '규제 대상 회원'
    ,   CONSTRAINT pk_id PRIMARY KEY (id)
    ,   CONSTRAINT fk_regulation_member_id FOREIGN KEY (member_id) REFERENCES MEMBER (id)
);



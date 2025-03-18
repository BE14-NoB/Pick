-- 기존 테이블 삭제
DROP TABLE IF EXISTS PROJECT_MEETING_IMAGE CASCADE;	-- 프로젝트 회의록 사진
DROP TABLE IF EXISTS PROJECT_MEETING CASCADE;			-- 프로젝트 회의록 
DROP TABLE IF EXISTS PROJECT_REVIEW CASCADE;				-- 프로젝트 후기
DROP TABLE IF EXISTS MEMBER_REVIEW CASCADE;				-- 회원 후기 
DROP TABLE IF EXISTS PARTICIPANT CASCADE;					-- 프로젝트 팀원
DROP TABLE IF EXISTS PROJECT_ROOM CASCADE;				-- 프로젝트 방


-- 프로젝트 관련 DDL -------------------------------------------------------------------------

-- 프로젝트 방 테이블
CREATE TABLE if NOT EXISTS project_room
(
	  id	INTEGER NOT NULL AUTO_INCREMENT         	 COMMENT '프로젝트 방 번호'
	, name VARCHAR(255) NOT NULL					     COMMENT '프로젝트 이름'
	, content TEXT	NOT NULL							 COMMENT '프로젝트 소개'
	, is_finished VARCHAR(4) NOT NULL DEFAULT 'N'	     COMMENT '프로젝트 완료 여부'
	, is_deleted VARCHAR(4) NOT NULL DEFAULT 'N'         COMMENT '프로젝트 삭제 여부'
    , duration_time VARCHAR(100) NOT NULL			     COMMENT '프로젝트 기간'
	, maximum_participant INTEGER NOT NULL				 COMMENT '프로젝트 최대 인원'
	, session_code INTEGER NULL UNIQUE					 COMMENT '(자율매칭)프로젝트 입장 코드'
	, start_date VARCHAR(255) NOT NULL					 COMMENT '프로젝트 시작일'
	, end_date VARCHAR(255) NOT NULL 					 COMMENT '프로젝트 종료일'
	, technology_category_id INTEGER NOT NULL 			 COMMENT '프로젝트 기술 분류'
	, thumbnail_image VARCHAR(255) UNIQUE                COMMENT '프로젝트 썸네일 이미지'
    , introduction TEXT                                  COMMENT '프로젝트 한줄 소개'
    , project_url   VARCHAR(255) UNIQUE                  COMMENT '프로젝트 링크'
    , CONSTRAINT pk_id PRIMARY KEY (id)
    , CONSTRAINT fk_project_technology_category_id FOREIGN KEY (technology_category_id) REFERENCES TECHNOLOGY_CATEGORY (id)
    , CHECK (maximum_participant > 0)
);


-- 팀원 테이블 
CREATE TABLE if NOT EXISTS PARTICIPANT 
(
	  id INTEGER NOT NULL AUTO_INCREMENT 	            COMMENT '팀원 번호'
	, is_manager VARCHAR(4) NOT NULL DEFAULT 'N'      COMMENT '방장 여부'
	, project_room_id INTEGER NOT NULL					COMMENT '프로젝트 방 번호'
	, member_id INTEGER NOT NULL						COMMENT '회원 번호'
    , CONSTRAINT pk_id PRIMARY KEY (id)
	, CONSTRAINT fk_participant_project_room_id FOREIGN KEY (project_room_id) REFERENCES PROJECT_ROOM (id)
	, CONSTRAINT fk_participant_member_id FOREIGN KEY (member_id) REFERENCES MEMBER (id)
	, UNIQUE (project_room_id, member_id)
);


-- 팀원 후기 테이블
CREATE TABLE if NOT EXISTS MEMBER_REVIEW 
(
	  id	INTEGER NOT NULL AUTO_INCREMENT             		COMMENT '팀원 후기 번호'
	, rate INTEGER NOT NULL CHECK (rate >= 1 AND rate <= 5)	    COMMENT '팀원 후기 평점'
	, content TEXT 									            COMMENT '팀원 후기 내용'
	, reviewer_id INTEGER NOT NULL	 							COMMENT '후기 작성자'
	, reviewee_id INTEGER NOT NULL 								COMMENT '후기 대상자'
	, CONSTRAINT pk_id PRIMARY KEY (id)
    , CONSTRAINT fk_member_review_reviewer_id FOREIGN KEY (reviewer_id) REFERENCES PARTICIPANT (id)
	, CONSTRAINT fk_member_review_reviewee_id FOREIGN KEY (reviewee_id) REFERENCES PARTICIPANT (id)
);



-- 프로젝트 후기 테이블
CREATE TABLE if NOT EXISTS PROJECT_REVIEW 
(
	  id	INTEGER NOT NULL AUTO_INCREMENT         		COMMENT '프로젝트 후기 번호'
	, reviewer_id INTEGER NOT NULL							COMMENT '후기 작성자'
	, content TEXT	NOT NULL								COMMENT '후기 내용'
	, project_room_id INTEGER NOT NULL
	, CONSTRAINT pk_id PRIMARY KEY (id)
    , CONSTRAINT fk_project_review_project_room_id  FOREIGN KEY (project_room_id) REFERENCES PROJECT_ROOM (id)
	, CONSTRAINT fk_preojct_review_reviewer_id  FOREIGN KEY (reviewer_id) REFERENCES PARTICIPANT (id)
);


-- 프로젝트 회의록
CREATE TABLE if NOT EXISTS PROJECT_MEETING
(
	  id INTEGER NOT NULL AUTO_INCREMENT            	COMMENT '회의록 번호'
	, project_room_id INTEGER NOT NULL					COMMENT '프로젝트 방 번호'
	, title VARCHAR(255) NOT NULL						COMMENT '회의록 제목'
	, content TEXT	NOT NULL							COMMENT '회의록 내용'
	, author	VARCHAR(255) NOT NULL					COMMENT '회의록 작성자'
	, upload_time VARCHAR(255) NOT NULL				    COMMENT '작성일'
	, update_time	VARCHAR(255) 				        COMMENT '수정일'
    , CONSTRAINT pk_id PRIMARY KEY (id)
	, CONSTRAINT fk_proejct_meeting_project_room_id FOREIGN KEY (project_room_id) REFERENCES PROJECT_ROOM (id)
);


-- 프로젝트 회의록 사진
CREATE TABLE if NOT EXISTS project_meeting_image
(
	  id INTEGER NOT NULL AUTO_INCREMENT	            COMMENT '회의록 사진 번호'
	, image_path VARCHAR(255) NOT NULL 					COMMENT '사진 경로'
	, image_name VARCHAR(255) NOT NULL					COMMENT '재정의 사진명'
	, is_thumbnail VARCHAR(4)                      		COMMENT '썸네일 여부'
	, meeting_id INTEGER NOT NULL						COMMENT '회의록 번호'
    , CONSTRAINT pk_id PRIMARY KEY (id)
	, CONSTRAINT fk_project_meeting_image_meeting_id FOREIGN KEY (meeting_id) REFERENCES PROJECT_MEETING (id)
);



DESC PROJECT_MEETING_IMAGE;	-- 프로젝트 회의록 사진
DESC PROJECT_MEETING;		-- 프로젝트 회의록 
DESC PROJECT_REVIEW;		-- 프로젝트 후기
DESC PROJECT_MEETING;		-- 프로젝트 회의록 
DESC MEMBER_REVIEW;			-- 회원 후기
DESC PARTICIPANT;			-- 프로젝트 팀원
DESC PROJECT_ROOM;			-- 프로젝트 방
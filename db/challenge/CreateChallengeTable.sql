DROP TABLE IF EXISTS MEMBER_BADGE;
DROP TABLE IF EXISTS MEMBER_DAILY_MISSION;
DROP TABLE IF EXISTS MEMBER_ACHIEVEMENT;
DROP TABLE IF EXISTS BADGE;
DROP TABLE IF EXISTS DAILY_MISSION;
DROP TABLE IF EXISTS ACHIEVEMENT;
DROP TABLE IF EXISTS CHALLENGE;

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
    id           INTEGER      NOT NULL AUTO_INCREMENT
,   name         VARCHAR(255) NOT NULL
,   requirement  INTEGER
,   advantage    INTEGER      NOT NULL
,   description  TEXT         NOT NULL
,   is_deleted   VARCHAR(4)   NOT NULL DEFAULT 'N'
,   challenge_id INTEGER      NOT NULL
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

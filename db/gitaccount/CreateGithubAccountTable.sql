DROP TABLE IF EXISTS GITHUB_ACCOUNT CASCADE;

CREATE TABLE IF NOT EXISTS GITHUB_ACCOUNT
(
    id           INT          AUTO_INCREMENT         COMMENT '깃 계정 번호'
,   user_id      VARCHAR(255) NOT NULL UNIQUE        COMMENT '깃 계정 id'
,   access_token VARCHAR(255)                        COMMENT '깃 인증 토큰'
,   CONSTRAINT pk_id PRIMARY KEY (id)
);
DROP TABLE IF EXISTS MATCING_ENTRY CASCADE;
DROP TABLE IF EXISTS MATCHING CASCADE;
DROP TABLE IF EXISTS TECHNOLOGY_CATEGORY CASCADE;

CREATE TABLE IF NOT EXISTS TECHNOLOGY_CATEGORY 
(
  id                         INTEGER      NOT NULL AUTO_INCREMENT
, name                       VARCHAR(255) NOT NULL UNIQUE
, is_deleted                 VARCHAR(4)   NOT NULL DEFAULT 'N'
, ref_technology_category_id INTEGER      NULL
, CONSTRAINT pk_id PRIMARY KEY (id)
, CONSTRAINT fk_technology_category_id FOREIGN KEY (ref_technology_category_id) REFERENCES TECHNOLOGY_CATEGORY (id)
);

CREATE TABLE IF NOT EXISTS MATCHING
(
  id                     INTEGER      NOT NULL AUTO_INCREMENT
, created_date_at        VARCHAR(255) NOT NULL
, is_completed           VARCHAR(4)   NOT NULL DEFAULT 'N'
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
, member_id    INTEGER      NOT NULL
, matching_id  INTEGER      NOT NULL
, CONSTRAINT pk_id PRIMARY KEY (id)
, CONSTRAINT fk_matching_entry_member_id FOREIGN KEY (member_id) REFERENCES MEMBER(id)
, CONSTRAINT fk_matching_entry_matching_id FOREIGN KEY (matching_id) REFERENCES MATCHING(id)
);

-- ==========================================
-- 프로젝트 관리 시스템 SELECT 쿼리 모음
-- ==========================================

-- ========================
-- 프로젝트 방 전체 목록 조회 (진행 중)
-- ========================
SELECT
    pr.id,
    pr.name,
    pr.content,
    pr.is_finished,
    pr.duration_time,
    pr.maximum_participant,
    pr.session_code,
    pr.start_date,
    pr.end_date,
    tc.name AS technology_category
FROM
    project_room pr
        INNER JOIN
    technology_category tc
    ON pr.technology_category_id = tc.id
WHERE
    pr.is_finished = 'N';

-- ========================
-- 특정 프로젝트 방 상세 조회 (예: 1번)
-- ========================
SELECT
    pr.id,
    pr.name,
    pr.content,
    pr.is_finished,
    pr.duration_time,
    pr.maximum_participant,
    pr.session_code,
    pr.start_date,
    pr.end_date,
    tc.name AS technology_category
FROM
    project_room pr
        INNER JOIN
    technology_category tc
    ON pr.technology_category_id = tc.id
WHERE
    pr.id = 1;   -- #{projectRoomId}

-- ========================
-- 특정 프로젝트 팀원 목록 조회 (예: 1번 프로젝트)
-- ========================
SELECT
    p.id AS participant_id,
    m.id AS member_id,
    m.name,
    m.nickname,
    p.is_manager
FROM
    participant p
        INNER JOIN
    member m
    ON p.member_id = m.id
WHERE
    p.project_room_id = 1;   -- #{projectRoomId}

-- ========================
-- 전체 팀원 후기 목록 조회
-- ========================
SELECT
    *
FROM
    member_review;

-- ========================
-- 특정 팀원 후기 목록 조회 (예: 멤버 2번)
-- ========================
SELECT
    mr.id,
    mr.rate,
    mr.content,
    mr.reviewer_id,
    r_member.nickname AS reviewer_nickname
FROM
    member_review mr
        INNER JOIN
    participant p
    ON mr.reviewee_id = p.id
        INNER JOIN
    participant rp
    ON mr.reviewer_id = rp.id
        INNER JOIN
    member r_member
    ON rp.member_id = r_member.id
WHERE
    p.member_id = 2;   -- #{memberId}

-- ========================
-- 특정 프로젝트 후기 목록 조회 (예: 1번 프로젝트)
-- ========================
SELECT
    pr.id,
    pr.content,
    pr.reviewer_id,
    m.nickname AS reviewer_nickname
FROM
    project_review pr
        INNER JOIN
    participant p
    ON pr.reviewer_id = p.id
        INNER JOIN
    member m
    ON p.member_id = m.id
WHERE
    pr.project_room_id = 1;   -- #{projectRoomId}

-- ========================
-- 특정 프로젝트 회의록 목록 조회 (예: 2번 프로젝트)
-- ========================
SELECT
    pm.id,
    pm.title,
    pm.content,
    pm.author,
    pm.upload_time,
    pm.update_time
FROM
    project_meeting pm
WHERE
    pm.project_room_id = 2;   -- #{projectRoomId}

-- ========================
-- 특정 회의록의 사진 목록 조회 (예: 회의록 1번)
-- ========================
SELECT
    pmi.id,
    pmi.image_path,
    pmi.image_name,
    pmi.is_thumbnail
FROM
    project_meeting_image pmi
WHERE
    pmi.meeting_id = 1;   -- #{meetingId}

-- ========================
-- 프로젝트 방 검색 (이름, 기술 카테고리 기준)
-- 예시: 백엔드(카테고리 1번) 검색 + 이름 검색
-- ========================
SELECT
    pr.id,
    pr.name,
    pr.content,
    pr.start_date,
    pr.end_date,
    tc.name AS technology_category
FROM
    project_room pr
        INNER JOIN
    technology_category tc
    ON pr.technology_category_id = tc.id
WHERE
    tc.id = 1                -- #{categoryId}
  AND pr.name LIKE CONCAT('%', '검색어', '%');   -- #{searchKeyword}



--  특정 프로젝트에 대한 후기 목록 조회

SELECT
       pr.id                AS review_id
     , pr.content           AS review_content
     , pr.reviewer_id       AS reviewer_participant_id
     , p.member_id          AS reviewer_member_id
     , m.`name` 		    AS reviewer_name
     , m.nickname           AS reviewer_nickname
     , pr.project_room_id   AS project_room_id
FROM PROJECT_REVIEW pr
         JOIN PARTICIPANT p
              ON pr.reviewer_id = p.id
         JOIN MEMBER m
              ON p.member_id = m.id
WHERE pr.project_room_id = ?
ORDER BY pr.id DESC;


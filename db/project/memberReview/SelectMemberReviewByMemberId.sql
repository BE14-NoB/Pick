-- 특정 회원이 받은 팀원 후기 목록 조회

SELECT
       mr.id                    AS review_id
     , mr.rate                  AS rating
     , mr.content               AS review_content
     , reviewer.member_id       AS reviewer_member_id
     , reviewer_member.nickname AS reviewer_nickname
     , pr.name                  AS project_name
FROM MEMBER_REVIEW mr
         JOIN PARTICIPANT reviewee
              ON mr.reviewee_id = reviewee.id
         JOIN PARTICIPANT reviewer
              ON mr.reviewer_id = reviewer.id
         JOIN MEMBER reviewer_member
              ON reviewer.member_id = reviewer_member.id
         JOIN PROJECT_ROOM pr
              ON reviewer.project_room_id = pr.id
WHERE reviewee.member_id = ?
ORDER BY mr.id DESC;


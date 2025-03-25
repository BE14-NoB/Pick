-- 특정 프로젝트에 참여하는 팀원 조회

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
    p.project_room_id = ?;   -- #{projectRoomId}
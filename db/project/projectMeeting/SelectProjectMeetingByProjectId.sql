-- 특정 프로젝트 방에서 작성된 회의록 목록

SELECT
       id                    AS meeting_id
     , project_room_id       AS project_room_id
     , title                 AS meeting_title
     , author_id             AS author
     , upload_time           AS upload_time
     , update_time           AS update_time
    FROM project_meeting
        WHERE project_room_id = ?
ORDER BY upload_time DESC;
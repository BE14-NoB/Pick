-- 특정 회의록 첨부 사진 조회

SELECT
       id                  AS image_id
     , image_path          AS image_path
     , image_name          AS image_name
     , is_thumbnail        AS is_thumbnail
    FROM PROJECT_MEETING_IMAGE
        WHERE meeting_id = ?
ORDER BY id ASC;

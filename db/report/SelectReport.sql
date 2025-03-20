-- 신고 목록 조회 (전체 조회)
SELECT
    R.ID
     , R.REPORTED_AT
     , R.STATUS
     , R.CATEGORY
     , R.REPORTED_ID
     , R.REPORT_REASON_ID
     , R.MEMBER_ID
FROM REPORT R;


-- 신고 상세 조회 (회원 1번)
-- 회원(직접 신고된 경우)
SELECT
    R.ID
     , R.REPORTED_AT
     , R.STATUS
     , R.CATEGORY
     , R.REPORTED_ID
     , R.REPORT_REASON_ID
     , R.MEMBER_ID
FROM REPORT R
WHERE R.CATEGORY = 0 AND R.REPORTED_ID = 1; # #{parameterType}

-- 게시글(POST 테이블을 통해 회원 1번이 작성한 게시글이 신고된 경우)
SELECT
       R.id
     , R.reported_at
     , R.status
     , R.category
     , R.reported_id
     , RR.reason
     , P.id AS post_id
     , P.member_id
  FROM REPORT R
  JOIN REPORT_REASON RR ON R.report_reason_id = RR.id
  JOIN POST P ON P.id = R.reported_id  -- REPORT 테이블과 POST 테이블을 조인
 WHERE R.category = 1
   AND P.member_id = 1;

-- 댓글(COMMENT 테이블을 통해 회원 1번이 작성한 댓글이 신고된 경우)
SELECT
       R.id
     , R.reported_at
     , R.status
     , R.category
     , R.reported_id
     , RR.reason
     , C.id
     , C.member_id
  FROM REPORT R
  JOIN REPORT_REASON RR ON R.report_reason_id = RR.id
  JOIN COMMENT C ON C.id = R.reported_id
WHERE R.category = 2
  AND C.member_id = 1;

-- 1번 회원 직접 신고 or 1번 회원이 작성한 게시글 신고 or 1번 회원이 작성한 댓글 신고
-- 관리자가 이 신고를 승인한 경우 (status = 2)
SELECT
       R.id
     , R.reported_at
     , R.status
     , R.category
     , R.reported_id
     , RR.reason
     , COALESCE(P.id, C.id) AS post_or_comment_id                      -- 게시글 또는 댓글 ID
     , COALESCE(P.member_id, C.member_id) AS post_or_comment_member_id -- 게시글 또는 댓글 작성자 ID
  FROM REPORT R
  JOIN REPORT_REASON RR ON R.report_reason_id = RR.id
  LEFT JOIN POST P ON R.category = 1 AND P.id = R.reported_id AND P.member_id = 1
  LEFT JOIN COMMENT C ON R.category = 2 AND C.id = R.reported_id AND C.member_id = 1
 WHERE R.status = 2
   AND (
           (R.category = 0 AND R.reported_id = 1)  -- 회원이 신고된 경우
        OR (R.category = 1 AND P.id IS NOT NULL)   -- 게시글이 신고된 경우
        OR (R.category = 2 AND C.id IS NOT NULL)   -- 댓글이 신고된 경우
       );


-- 규제 목록 조회
SELECT
       RG.id
     , RG.start_date
     , RG.end_date
     , M.id
  FROM REGULATION RG
  JOIN MEMBER M ON RG.member_id = M.id;

-- 규제 상세 조회 (회원 1번)
SELECT
       RG.id
     , RG.start_date
     , RG.end_date
     , M.id
  FROM REGULATION RG
  JOIN MEMBER M ON RG.member_id = M.id
 WHERE RG.member_id = 1;
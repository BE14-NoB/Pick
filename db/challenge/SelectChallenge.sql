-- 도전과제 목록 챌린지 이름과 함께 조회(삭제되지 않은)
SELECT
       A.id
     , C.name
     , A.requirement
     , A.exp
     , A.is_deleted
  FROM ACHIEVEMENT A
  JOIN CHALLENGE C ON A.challenge_id = C.id
 WHERE is_deleted = 'N';

-- 특정 회원의 도전과제 진행도 및 달성 횟수 조회
SELECT
       A.id
     , C.name
     , A.requirement
     , MA.progress
     , MA.achieved_time
  FROM MEMBER_ACHIEVEMENT MA
  JOIN ACHIEVEMENT A ON MA.achievement_id = A.id
  JOIN CHALLENGE C ON A.challenge_id = C.id
 WHERE MA.member_id = ?;

-- 일일미션 목록 전체 조회(삭제되지 않은)
SELECT
       id
     , content
     , exp_point
     , is_deleted
  FROM DAILY_MISSION
 WHERE is_deleted = 'N';

-- 특정 회원의 일일미션 현황 조회
SELECT
       DM.id
     , DM.content
     , DM.exp_point
     , MDM.is_completed
     , MDM.accepted_date
  FROM MEMBER_DAILY_MISSION MDM
  JOIN DAILY_MISSION DM ON MDM.daily_mission_id = DM.id
 WHERE MDM.member_id = ?
   AND DM.is_deleted = 'N';

-- 특정 회원의 일일미션 달성 내역 조회 (완료된 미션만)
SELECT
       M.nickname
     , DM.content
     , MDM.accepted_date
  FROM MEMBER_DAILY_MISSION MDM
  JOIN DAILY_MISSION DM ON MDM.daily_mission_id = DM.id
  JOIN MEMBER M ON MDM.member_id = M.id
 WHERE MDM.is_completed = 'Y'
   AND M.id = ?;

-- 뱃지 목록 전체 조회(삭제되지 않은)
SELECT
       id
     , name
     , requirement
     , advantage
     , description
     , is_deleted
     , challenge_id
  FROM BADGE
 WHERE is_deleted = 'N';

-- 특정 회원이 획득한 뱃지 조회
SELECT
       MB.id
     , MB.acquired_date
     , MB.level
     , MB.badge_id
     , MB.member_id
     , B.name
     , B.description
  FROM MEMBER_BADGE MB
  JOIN BADGE B ON MB.badge_id = B.id
 WHERE MB.member_id = ?;

-- 챌린지별 일일미션 목록 조회
SELECT
       C.name
     , DM.content
     , DM.exp_point
  FROM DAILY_MISSION DM
  JOIN CHALLENGE C ON DM.challenge_id = C.id;
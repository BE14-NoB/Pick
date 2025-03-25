-- ==========================================
-- 더미 데이터 INSERT
-- ==========================================

-- MEMBER 테이블 (회원 20명)
INSERT INTO MEMBER
(
    name,
    age,
    ihidnum,
    phone_number,
    email,
    password,
    nickname,
    status,
    reported_count,
    user_grant
)
VALUES
    ('홍길동', 25, '900101-1234567', '010-1111-0001', 'user1@example.com', 'hashed_pw_1', 'nick1', 1, 0, 1),
    ('김영희', 28, '900102-2345678', '010-1111-0002', 'user2@example.com', 'hashed_pw_2', 'nick2', 1, 0, 1),
    ('이철수', 30, '900103-3456789', '010-1111-0003', 'user3@example.com', 'hashed_pw_3', 'nick3', 2, 0, 1),
    ('박민수', 27, '900104-4567890', '010-1111-0004', 'user4@example.com', 'hashed_pw_4', 'nick4', 1, 0, 1),
    ('최지은', 26, '900105-5678901', '010-1111-0005', 'user5@example.com', 'hashed_pw_5', 'nick5', 1, 1, 1),
    ('한지민', 29, '900106-6789012', '010-1111-0006', 'user6@example.com', 'hashed_pw_6', 'nick6', 1, 0, 2),
    ('윤상혁', 32, '900107-7890123', '010-1111-0007', 'user7@example.com', 'hashed_pw_7', 'nick7', 1, 2, 1),
    ('오세훈', 24, '900108-8901234', '010-1111-0008', 'user8@example.com', 'hashed_pw_8', 'nick8', 1, 0, 1),
    ('백현수', 31, '900109-9012345', '010-1111-0009', 'user9@example.com', 'hashed_pw_9', 'nick9', 1, 0, 1),
    ('장예린', 29, '900110-0123456', '010-1111-0010', 'user10@example.com', 'hashed_pw_10', 'nick10', 1, 0, 1),
    ('서준호', 25, '900111-1234567', '010-1111-0011', 'user11@example.com', 'hashed_pw_11', 'nick11', 1, 0, 1),
    ('배성우', 28, '900112-2345678', '010-1111-0012', 'user12@example.com', 'hashed_pw_12', 'nick12', 1, 0, 1),
    ('정미나', 30, '900113-3456789', '010-1111-0013', 'user13@example.com', 'hashed_pw_13', 'nick13', 1, 0, 1),
    ('고윤희', 26, '900114-4567890', '010-1111-0014', 'user14@example.com', 'hashed_pw_14', 'nick14', 1, 0, 1),
    ('안수민', 27, '900115-5678901', '010-1111-0015', 'user15@example.com', 'hashed_pw_15', 'nick15', 1, 0, 1),
    ('문지훈', 29, '900116-6789012', '010-1111-0016', 'user16@example.com', 'hashed_pw_16', 'nick16', 1, 0, 1),
    ('정유진', 25, '900117-7890123', '010-1111-0017', 'user17@example.com', 'hashed_pw_17', 'nick17', 1, 0, 1),
    ('하도연', 30, '900118-8901234', '010-1111-0018', 'user18@example.com', 'hashed_pw_18', 'nick18', 1, 0, 1),
    ('홍서연', 28, '900119-9012345', '010-1111-0019', 'user19@example.com', 'hashed_pw_19', 'nick19', 1, 0, 1),
    ('권민수', 27, '900120-0123456', '010-1111-0020', 'user20@example.com', 'hashed_pw_20', 'nick20', 1, 0, 1);

-- TECHNOLOGY_CATEGORY 테이블
INSERT INTO TECHNOLOGY_CATEGORY
(id, name, is_deleted, ref_technology_category_id)
VALUES
    (1, '백엔드',      'N', NULL),
    (2, '프론트엔드',  'N', NULL),
    (3, '풀스택',      'N', 1),
    (4, 'AI',         'N', NULL);

-- PROJECT_ROOM 테이블
INSERT INTO PROJECT_ROOM
(name, content, is_finished, duration_time, maximum_participant, session_code, start_date, end_date, technology_category_id)
VALUES
    ('AI 프로젝트',            'AI 개발 프로젝트입니다.',                'N', '3개월',  5, 1001, '2024-03-01', '2024-06-01', 4),
    ('웹앱 개발',              '모바일 웹앱 개발 프로젝트입니다.',       'N', '2개월',  4, 1002, '2024-04-01', '2024-06-01', 2),
    ('백엔드 프로젝트',        '백엔드 서버 구축 프로젝트입니다.',       'N', '4개월',  6, 1003, '2024-01-15', '2024-05-15', 1),
    ('프론트엔드 프로젝트',    '프론트엔드 디자인과 개발 프로젝트입니다.','N', '2개월',  3, 1004, '2024-02-01', '2024-04-01', 2),
    ('풀스택 프로젝트',        '풀스택 개발팀 프로젝트입니다.',          'N', '5개월',  7, 1005, '2024-03-10', '2024-08-10', 3);

-- PARTICIPANT 테이블
INSERT INTO PARTICIPANT
(is_manager, project_room_id, member_id)
VALUES
    ('Y', 1, 1), ('N', 1, 2), ('N', 1, 3), ('N', 1, 4), ('N', 1, 5),
    ('Y', 2, 6), ('N', 2, 7), ('N', 2, 8), ('N', 2, 9),
    ('Y', 3, 10), ('N', 3, 11), ('N', 3, 12), ('N', 3, 13), ('N', 3, 14), ('N', 3, 15),
    ('Y', 4, 16), ('N', 4, 17), ('N', 4, 18),
    ('Y', 5, 19), ('N', 5, 20), ('N', 5, 1), ('N', 5, 2), ('N', 5, 3), ('N', 5, 4), ('N', 5, 5);

-- MEMBER_REVIEW 테이블
INSERT INTO MEMBER_REVIEW
(rate, content, reviewer_id, reviewee_id)
VALUES
    (5, '매우 훌륭합니다.',        1,  2),
    (4, '성실한 팀원이에요.',      2,  3),
    (5, '의사소통이 원활해요.',    6,  7),
    (3, '조금 늦긴 했지만 잘했어요.', 10, 11),
    (5, '매우 만족합니다.',        16, 17),
    (4, '열심히 참여했어요.',      19, 20),
    (3, '추가적인 노력이 필요합니다.', 1, 3),
    (5, '기여도가 높아요.',        6, 8),
    (4, '책임감이 강합니다.',      10, 12),
    (5, '팀워크가 훌륭합니다.',    16, 18);

-- PROJECT_REVIEW 테이블
INSERT INTO PROJECT_REVIEW
(reviewer_id, content, project_room_id)
VALUES
    (1,  '좋은 프로젝트였습니다.',            1),
    (6,  '정말 유익했어요.',                  2),
    (10, '백엔드 경험을 많이 쌓았어요.',      3),
    (16, '프론트엔드 기술이 향상되었습니다.', 4),
    (19, '풀스택 개발이 처음이었는데 흥미로웠어요.', 5);

-- PROJECT_MEETING 테이블
INSERT INTO PROJECT_MEETING
(
    project_room_id,
    title,
    content,
    author,
    upload_time,
    update_time
)
VALUES
    (1, 'AI 프로젝트 킥오프',        'AI 프로젝트 첫 회의입니다.',        1,  '2024-03-01 10:00:00', '2024-03-01 11:00:00'),
    (2, '웹앱 디자인 회의',          'UI/UX 논의 내용입니다.',            6,  '2024-04-02 10:00:00', '2024-04-02 11:00:00'),
    (3, '백엔드 구조 회의',          '서버 아키텍처 논의입니다.',         10, '2024-01-20 14:00:00', '2024-01-20 15:00:00'),
    (4, '프론트엔드 프레임워크 논의', 'React vs Vue 논쟁.',              16, '2024-02-05 13:00:00', '2024-02-05 14:00:00'),
    (5, '풀스택 일정 조율',          '전체 일정 조율 회의입니다.',        19, '2024-03-15 15:00:00', '2024-03-15 16:00:00');


-- PROJECT_MEETING_IMAGE 테이블
INSERT INTO PROJECT_MEETING_IMAGE
(image_path, image_name, is_thumbnail, meeting_id)
VALUES
    ('/images/ai_kickoff.png',          'ai_kickoff_renamed.png',          'Y', 1),
    ('/images/webapp_design.png',       'webapp_design_renamed.png',       'N', 2),
    ('/images/backend_structure.png',   'backend_structure_renamed.png',   'Y', 3),
    ('/images/frontend_framework.png',  'frontend_framework_renamed.png',  'N', 4),
    ('/images/fullstack_schedule.png',  'fullstack_schedule_renamed.png',  'Y', 5);
-- PROJECT_MEETING 회의록 테이블
INSERT INTO PROJECT_MEETING
(
    project_room_id,
    title,
    content,
    author_id,           -- member_id로 참조됨
    upload_time,
    update_time
)
VALUES
    (1, 'AI 프로젝트 킥오프',        'AI 프로젝트 첫 회의입니다.',        1,  '2024-03-01 10:00:00', '2024-03-01 11:00:00'),
    (2, '웹앱 디자인 회의',          'UI/UX 논의 내용입니다.',            6,  '2024-04-02 10:00:00', '2024-04-02 11:00:00'),
    (3, '백엔드 구조 회의',          '서버 아키텍처 논의입니다.',         10, '2024-01-20 14:00:00', '2024-01-20 15:00:00'),
    (4, '프론트엔드 프레임워크 논의', 'React vs Vue 논쟁.',              16, '2024-02-05 13:00:00', '2024-02-05 14:00:00'),
    (5, '풀스택 일정 조율',          '전체 일정 조율 회의입니다.',        19, '2024-03-15 15:00:00', '2024-03-15 16:00:00');

-- PROJECT_MEETING_IMAGE 회의록 이미지 테이블
INSERT INTO PROJECT_MEETING_IMAGE
(
    image_path,
    image_name,
    is_thumbnail,
    meeting_id
)
VALUES
    ('/images/ai_kickoff.png',          'ai_kickoff_renamed.png',          'Y', 1),
    ('/images/webapp_design.png',       'webapp_design_renamed.png',       'N', 2),
    ('/images/backend_structure.png',   'backend_structure_renamed.png',   'Y', 3),
    ('/images/frontend_framework.png',  'frontend_framework_renamed.png',  'N', 4),
    ('/images/fullstack_schedule.png',  'fullstack_schedule_renamed.png',  'Y', 5);
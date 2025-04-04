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





-- 회의록 템플릿 더미 데이터
INSERT INTO PROJECT_MEETING_TEMPALTE
    (name, description, type, content, is_default) VALUES
    ('스프린트 회의 템플릿', '스프린트 진행 상황을 점검하는 템플릿입니다.', 0, '# Sprint\n- 진행 상황\n- 이슈 사항\n- 다음 할 일', 'Y'),
    ('브레인스토밍 템플릿', '아이디어를 자유롭게 공유할 수 있는 템플릿입니다.', 1, '# Brainstorming\n- 아이디어 목록\n- 논의 요점\n- 정리된 결과', 'N'),
    ('짧은 회의 템플릿', '간단한 회의에 적합한 템플릿입니다.', 2, '# Short Meeting\n- 안건\n- 빠른 결론\n- 담당자 지정', 'N'),
    ('1:1 회의 템플릿', '개인 간 회의에 사용하는 템플릿입니다.', 3, '# One-on-One\n- 근황\n- 피드백\n- 목표 설정', 'N'),
    ('전체 회의 템플릿', '전 팀원이 참여하는 회의에 적합한 템플릿입니다.', 4, '# All Hands\n- 부서별 진행 상황\n- 주요 공지사항\n- Q&A', 'Y'),
    ('회고 회의 템플릿', '회고를 통해 개선점을 찾는 템플릿입니다.', 5, '# Retrospective\n- 잘한 점\n- 아쉬운 점\n- 개선 방안', 'N');

INSERT INTO PROJECT_MEETING_USED_TEMPLATE (meeting_id, template_id) VALUES
    (1, 1), -- AI 프로젝트 킥오프 => 스프린트 템플릿
    (2, 4), -- 웹앱 디자인 회의 => 1:1 회의 템플릿
    (3, 5), -- 백엔드 구조 회의 => 전체 회의 템플릿
    (4, 2), -- 프론트엔드 프레임워크 논의 => 브레인스토밍 템플릿
    (5, 6); -- 풀스택 일정 조율 => 회고 템플릿


-- 회의록 템플릿 더미 데이터 (구체적인 콘텐츠 포함)
INSERT INTO PROJECT_MEETING_TEMPLATE
(name, description, type, content, is_default)
VALUES
-- 0: 스프린트
('스프린트 회의 템플릿', '스프린트 진행 상황을 점검하는 템플릿입니다.', 0,
 '# Sprint 회의록 🏃‍♀️\n\n## Sprint 기간\n- 시작일: YYYY-MM-DD\n- 종료일: YYYY-MM-DD\n\n## 지난 Sprint 요약\n- ✅ 완료한 일\n  - [ ] 작업 1\n  - [ ] 작업 2\n- ❗️ 미완료된 일\n  - [ ] 작업 3\n\n## 이슈 및 장애\n| 이슈 | 영향 | 해결방안 |\n|------|------|----------|\n| 로그인 실패 | 사용자 로그인 불가 | 인증 서버 점검 필요 |\n\n## 다음 Sprint 목표\n- [ ] 목표 1\n- [ ] 목표 2\n\n## 공유사항\n- 팀 외 전달해야 할 내용 정리\n\n## 결정사항\n- 이 Sprint 내에 기능 A는 다음 Sprint로 이월',
 'Y'),

-- 1: 브레인스토밍
('브레인스토밍 템플릿', '아이디어를 자유롭게 공유할 수 있는 템플릿입니다.', 1,
 '# 브레인스토밍 회의록 🧠\n\n## 주제\n- ex: 신규 서비스 아이디어 도출\n\n## 제안된 아이디어\n1. 아이디어 A\n   - 장점:\n   - 단점:\n2. 아이디어 B\n   - 장점:\n   - 단점:\n\n## 토론 내용\n- 서로의 아이디어에 대한 피드백 정리\n\n## 투표 결과\n| 아이디어 | 득표 수 |\n|----------|---------|\n| A        | 3표     |\n| B        | 2표     |\n\n## 다음 단계\n- MVP로 개발할 아이디어 선정 및 책임자 지정',
 'N'),

-- 2: 짧은 회의
('짧은 회의 템플릿', '간단한 회의에 적합한 템플릿입니다.', 2,
 '# 짧은 회의 (Daily/Stand-up) ⏱️\n\n## 참여자\n- 홍길동\n- 김철수\n- 박민지\n\n## 오늘 할 일\n- [ ] 기능 A 테스트\n- [ ] 기능 B 배포\n\n## 장애/이슈\n- DB 연결 간헐적 오류 (10시 이후 발생)\n\n## 공지사항\n- 이번 주 데모는 금요일 16:00',
 'N'),

-- 3: 일대일 회의
('1:1 회의 템플릿', '개인 간 회의에 사용하는 템플릿입니다.', 3,
 '# 1:1 회의록 👤\n\n## 참여자\n- 멘토: 홍길동\n- 멘티: 김철수\n\n## 지난주 돌아보기\n- 업무 진행 상황\n- 어려웠던 점\n\n## 피드백\n- 👍 잘한 점\n- 📈 개선할 점\n\n## 다음 주 계획\n- [ ] 기술 스택 학습 정리 공유\n- [ ] 팀 프로젝트 진행\n\n## 멘토 코멘트\n> 계속해서 커뮤니케이션을 자주 하자!',
 'N'),

-- 4: 전체 회의
('전체 회의 템플릿', '전 팀원이 참여하는 회의에 적합한 템플릿입니다.', 4,
 '# 전체 회의 (All Hands) 🏢\n\n## 회의 일시\n- 날짜: YYYY-MM-DD\n- 시간: 오후 2시\n\n## 회사/팀 공지사항\n- 신규 입사자 소개\n- 조직 개편 안내\n\n## 성과 공유\n- Q1 주요 지표:\n  - DAU: 120%\n  - 신규 가입자: 1.5배 증가\n\n## Q&A\n| 질문 | 답변자 | 답변 요약 |\n|------|--------|------------|\n| OKR 기준은 누가 정하나요? | 박대표 | 각 팀이 자율적으로 정합니다 |\n\n## 다음 회의 예정\n- YYYY-MM-DD 오후 2시',
 'Y'),

-- 5: 회고 회의
('회고 회의 템플릿', '회고를 통해 개선점을 찾는 템플릿입니다.', 5,
 '# 회고 회의록 🔍\n\n## Sprint 기간\n- YYYY-MM-DD ~ YYYY-MM-DD\n\n## 잘한 점 (Keep)\n- 코드 리뷰가 활발하게 이뤄짐\n- 일정 준수율 향상\n\n## 개선할 점 (Problem)\n- 문서화 부족\n- 커뮤니케이션 딜레이\n\n## 시도할 점 (Try)\n- 주간 문서 정리 타임 도입\n- 슬랙 알림 룰 수정\n\n## 감정 체크\n| 팀원 | 상태 (🙂 / 😐 / 😟) | 한 마디 |\n|------|--------------------|----------|\n| 김철수 | 🙂 | 이번 Sprint 좋았어요! |\n| 박민지 | 😟 | 일정이 너무 타이트했어요 |\n\n## 마무리\n> 다음 회고는 2주 후입니다.',
 'N');



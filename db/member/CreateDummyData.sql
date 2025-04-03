INSERT INTO MEMBER (name, age, ihidnum, phone_number, email, password, nickname, status, regulated_count, user_grant)
VALUES ('김민수', 25, '950101-1234567', '010-1234-5678', 'minsu1@naver.com', 'Pass1234', '민수킹', 0, 1, 0),
       ('이영희', 30, '930215-2345678', '010-2345-6789', 'younghee2@gmail.com', 'Young567', '영희짱', 1, 0, 0),
       ('박지훈', 28, '970430-3456789', '010-3456-7890', 'jihoon3@daum.net', 'Jihoon89', '훈이', 0, 2, 0),
       ('최수진', 22, '990812-4567890', '010-4567-8901', 'sujin4@yahoo.com', 'Sujin101', '수진이', 2, 0, 1),
       ('강태우', 35, '880527-5678901', '010-5678-9012', 'taewoo5@kakao.com', 'Tae12345', '태우님', 0, 3, 0),
       ('윤서연', 27, '960615-1234567', '010-6789-0123', 'seoyeon6@naver.com', 'Seo1234', '서연', 0, 1, 0),
       ('정하늘', 33, '910309-2345678', '010-7890-1234', 'haneul7@gmail.com', 'Haneul56', '하늘님', 1, 2, 0),
       ('한지민', 24, '980722-3456789', '010-8901-2345', 'jimin8@daum.net', 'Jimin78', '지민이', 0, 0, 0),
       ('오준영', 29, '940115-4567890', '010-9012-3456', 'junyoung9@yahoo.com', 'Jun123', '준영', 2, 1, 0),
       ('신동현', 31, '920831-5678901', '010-0123-4567', 'donghyun10@kakao.com', 'Dong567', '동현', 0, 3, 1),
       ('김지영', 26, '950207-1234567', '010-1111-2222', 'jiyoung11@naver.com', 'Ji12345', '지영이', 0, 0, 0),
       ('이수현', 32, '910514-2345678', '010-2222-3333', 'suhyun12@gmail.com', 'Su5678', '수현님', 1, 2, 0),
       ('박민재', 23, '980928-3456789', '010-3333-4444', 'minjae13@daum.net', 'Min901', '민재', 0, 1, 0),
       ('최윤아', 28, '960312-4567890', '010-4444-5555', 'yuna14@yahoo.com', 'Yuna123', '윤아짱', 2, 0, 0),
       ('강도훈', 34, '890619-5678901', '010-5555-6666', 'dohoon15@kakao.com', 'Do5678', '도훈', 0, 3, 1),
       ('윤지우', 21, '000101-1234567', '010-6666-7777', 'jiwoo16@naver.com', 'Jiwoo12', '지우', 0, 1, 0),
       ('정소연', 29, '940225-2345678', '010-7777-8888', 'soyeon17@gmail.com', 'Soy123', '소연이', 1, 0, 0),
       ('한태희', 27, '970808-3456789', '010-8888-9999', 'taehee18@daum.net', 'Tae567', '태희', 0, 2, 0),
       ('오세진', 33, '910915-4567890', '010-9999-0000', 'sejin19@yahoo.com', 'Sej123', '세진님', 2, 1, 0),
       ('신하영', 25, '960502-5678901', '010-0000-1111', 'hayoung20@kakao.com', 'Hay567', '하영', 0, 0, 0),
       ('김영수', 30, '930710-1234567', '010-1212-3434', 'youngsoo21@naver.com', 'Young12', '영수킹', 0, 3, 1),
       ('이재훈', 28, '950824-2345678', '010-2323-4545', 'jaehoon22@gmail.com', 'Jae567', '재훈', 1, 2, 0),
       ('박소영', 24, '990106-3456789', '010-3434-5656', 'soyoung23@daum.net', 'Soy123', '소영이', 0, 0, 0),
       ('최민호', 31, '920413-4567890', '010-4545-6767', 'minho24@yahoo.com', 'Min567', '민호', 2, 1, 0),
       ('강지현', 26, '950527-5678901', '010-5656-7878', 'jihyeon25@kakao.com', 'Ji1234', '지현님', 0, 2, 0),
       ('윤태영', 32, '910701-1234567', '010-6767-8989', 'taeyoung26@naver.com', 'Tae567', '태영', 1, 0, 0),
       ('정민수', 23, '980814-2345678', '010-7878-9090', 'minsu27@gmail.com', 'Min123', '민수', 0, 3, 1),
       ('한서진', 29, '940927-3456789', '010-8989-0101', 'seojin28@daum.net', 'Seo567', '서진이', 2, 1, 0),
       ('오지훈', 35, '890210-4567890', '010-9090-1212', 'jihoon29@yahoo.com', 'Ji1234', '지훈님', 0, 0, 0),
       ('신윤서', 27, '960323-5678901', '010-0101-2323', 'yoonseo30@kakao.com', 'Yoon567', '윤서', 0, 2, 0);
INSERT INTO MEMBER_PROFILE_PAGE (exp, level, introduce, preferred_area, git_address, score, image_path, member_id)
VALUES (1500, 3, '안녕하세요, 김민수입니다.', '서울', 'https://github.com/minsu1', 85, '/images/minsu1.jpg', 1),
       (2000, 4, '코딩 좋아하는 이영희예요.', '부산', 'https://github.com/younghee2', 90, '/images/younghee2.png', 2),
       (800, 2, NULL, '대구', 'https://github.com/jihoon3', 70, '/images/jihoon3.jpg', 3),
       (500, 1, '신입 개발자 최수진입니다.', NULL, 'https://github.com/sujin4', NULL, NULL, 4),
       (3000, 5, '강태우, 10년차 개발자.', '인천', 'https://github.com/taewoo5', 95, '/images/taewoo5.jpeg', 5),
       (1200, 3, '윤서연이에요!', '광주', 'https://github.com/seoyeon6', 80, '/images/seoyeon6.jpg', 6),
       (1800, 4, '정하늘입니다.', '대전', 'https://github.com/haneul7', 88, '/images/haneul7.png', 7),
       (600, 2, NULL, '울산', 'https://github.com/jimin8', 65, '/images/jimin8.jpg', 8),
       (900, 2, '오준영, 열정적인 개발자.', '제주', 'https://github.com/junyoung9', 75, '/images/junyoung9.jpeg', 9),
       (2500, 5, '신동현입니다.', '서울', 'https://github.com/donghyun10', 92, '/images/donghyun10.png', 10),
       (1400, 3, '김지영이에요.', '부산', 'https://github.com/jiyoung11', 82, '/images/jiyoung11.jpg', 11),
       (1700, 4, NULL, '대구', 'https://github.com/suhyun12', 87, '/images/suhyun12.png', 12),
       (700, 2, '박민재입니다.', '인천', 'https://github.com/minjae13', 68, '/images/minjae13.jpg', 13),
       (1100, 3, '최윤아, 개발자 꿈나무.', NULL, 'https://github.com/yuna14', 78, '/images/yuna14.jpeg', 14),
       (2800, 5, '강도훈이에요.', '서울', 'https://github.com/dohoon15', 94, '/images/dohoon15.png', 15),
       (400, 1, '윤지우, 신입이에요.', '부산', 'https://github.com/jiwoo16', NULL, NULL, 16),
       (1300, 3, NULL, '대전', 'https://github.com/soyeon17', 83, '/images/soyeon17.jpg', 17),
       (1000, 2, '한태희입니다.', '광주', 'https://github.com/taehee18', 77, '/images/taehee18.png', 18),
       (1600, 4, '오세진이에요.', '울산', 'https://github.com/sejin19', 86, '/images/sejin19.jpeg', 19),
       (2200, 4, '신하영, 열심히 코딩중!', '제주', 'https://github.com/hayoung20', 89, '/images/hayoung20.jpg', 20),
       (1900, 4, '김영수입니다.', '서울', 'https://github.com/youngsoo21', 91, '/images/youngsoo21.png', 21),
       (300, 1, NULL, '부산', 'https://github.com/jaehoon22', NULL, NULL, 22),
       (850, 2, '박소영이에요.', '대구', 'https://github.com/soyoung23', 72, '/images/soyoung23.jpg', 23),
       (1450, 3, '최민호입니다.', '인천', 'https://github.com/minho24', 84, '/images/minho24.jpeg', 24),
       (2700, 5, '강지현, 개발자입니다.', '서울', 'https://github.com/jihyeon25', 93, '/images/jihyeon25.png', 25),
       (950, 2, NULL, '광주', 'https://github.com/taeyoung26', 76, '/images/taeyoung26.jpg', 26),
       (1750, 4, '정민수예요.', '대전', 'https://github.com/minsu27', 88, '/images/minsu27.png', 27),
       (650, 2, '한서진입니다.', '울산', 'https://github.com/seojin28', 69, '/images/seojin28.jpeg', 28),
       (2000, 4, '오지훈이에요.', '제주', 'https://github.com/jihoon29', 90, '/images/jihoon29.jpg', 29),
       (1250, 3, '신윤서, 코딩 좋아해요.', '서울', 'https://github.com/yoonseo30', 81, '/images/yoonseo30.png', 30);

INSERT INTO PROGRAMMING_LANGUAGE (language, is_deleted)
VALUES ('Java', 'N'),
       ('Python', 'N'),
       ('JavaScript', 'N'),
       ('C++', 'N'),
       ('Ruby', 'N'),
       ('Go', 'N'),
       ('Kotlin', 'N'),
       ('Swift', 'N'),
       ('PHP', 'N'),
       ('Rust', 'N'),
       ('TypeScript', 'N'),
       ('C#', 'N'),
       ('Scala', 'N'),
       ('Perl', 'N'),
       ('Haskell', 'N'),
       ('R', 'N'),
       ('Dart', 'N'),
       ('Lua', 'N'),
       ('MATLAB', 'N'),
       ('SQL', 'N'),
       ('Groovy', 'N'),
       ('Elixir', 'N'),
       ('Clojure', 'N'),
       ('F#', 'N'),
       ('VB.NET', 'N'),
       ('Assembly', 'N'),
       ('Objective-C', 'N'),
       ('Pascal', 'N'),
       ('Fortran', 'N'),
       ('COBOL', 'Y');


INSERT INTO MEMBER_PROGRAMMING_LANGUAGE (programming_language_id, member_profile_page_id)
VALUES (1, 1),   -- 김민수: Java
       (2, 1),   -- 김민수: Python
       (3, 2),   -- 이영희: JavaScript
       (4, 3),   -- 박지훈: C++
       (2, 5),   -- 강태우: Python
       (6, 6),   -- 윤서연: Go
       (7, 7),   -- 정하늘: Kotlin
       (8, 8),   -- 한지민: Swift
       (9, 9),   -- 오준영: PHP
       (10, 10), -- 신동현: Rust
       (11, 11), -- 김지영: TypeScript
       (12, 12), -- 이수현: C#
       (13, 13), -- 박민재: Scala
       (14, 14), -- 최윤아: Perl
       (15, 15), -- 강도훈: Haskell
       (16, 16), -- 윤지우: R
       (17, 17), -- 정소연: Dart
       (18, 18), -- 한태희: Lua
       (19, 19), -- 오세진: MATLAB
       (20, 20), -- 신하영: SQL
       (1, 21),  -- 김영수: Java
       (3, 22),  -- 이재훈: JavaScript
       (4, 23),  -- 박소영: C++
       (2, 24),  -- 최민호: Python
       (6, 25),  -- 강지현: Go
       (7, 26),  -- 윤태영: Kotlin
       (8, 27),  -- 정민수: Swift
       (9, 28),  -- 한서진: PHP
       (10, 29), -- 오지훈: Rust
       (11, 30); -- 신윤서: TypeScript
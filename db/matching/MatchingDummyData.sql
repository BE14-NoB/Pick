INSERT INTO TECHNOLOGY_CATEGORY
(
  name
, is_deleted
, ref_technology_category_id
)
VALUES
('PC', 'N', NULL)
, ('모바일', 'N', NULL)
, ('보안', 'N', NULL)
, ('AI', 'N', NULL)
, ('기타', 'N', NULL)
, ('웹', 'N', 1)
, ('게임', 'N', 1)
, ('게임', 'N', 2)
, ('VR/AR', 'N', 5)
, ('IoT', 'N', 5)
, ('안드로이드', 'N', 2)
, ('ios', 'N', 2)
, ('딥러닝', 'N', 4)
, ('머신러닝', 'N', 4)
, ('클라우드', 'N', 5)
, ('블록체인', 'N', 3);

INSERT INTO MATCHING 
(
  created_date_at
, is_completed
, is_deleted
, maximum_participant
, current_participant
, level_range
, member_id
, technology_category_id
) 
VALUES
('2024-03-01 10:30:00', 'N', 'N', 5, 3, 3, 1, 7),
('2024-03-02 12:45:00', 'N', 'N', 7, 1, 5, 6, 6),
('2024-03-03 15:00:00', 'N', 'N', 4, 3, 2, 18, 6),
('2024-03-04 09:20:00', 'N', 'N', 4, 3, 10, 14, 5),
('2024-03-05 14:10:00', 'Y', 'N', 5, 5, 7, 10, 4),
('2024-03-06 17:30:00', 'N', 'N', 6, 4, 6, 2, 9),
('2024-03-07 11:00:00', 'N', 'Y', 6, 4, 5, 5, 8),
('2024-03-08 16:40:00', 'N', 'N', 5, 2, 5, 3, 7),
('2024-03-09 08:55:00', 'Y', 'N', 4, 4, 11, 15, 9),
('2024-03-10 13:25:00', 'N', 'N', 8, 5, 12, 18, 2);

INSERT INTO MATCHING_ENTRY 
(
  applied_date_at
, is_canceled
, is_accepted
, member_id
, matching_id
) 
VALUES
('2024-03-01 11:00:00', 'N', 'N', 4, 1),
('2024-03-02 13:00:00', 'N', 'Y', 11, 2),
('2024-03-03 15:30:00', 'Y', 'N', 6, 3),
('2024-03-04 10:10:00', 'N', 'Y', 2, 4),
('2024-03-05 14:40:00', 'N', 'Y', 14, 5),
('2024-03-06 18:20:00', 'N', 'N', 10, 3),
('2024-03-07 12:30:00', 'Y', 'N', 8, 5),
('2024-03-08 17:50:00', 'N', 'N', 9, 2),
('2024-03-09 09:15:00', 'N', 'N', 13, 5),
('2024-03-10 14:00:00', 'N', 'N', 18, 4);
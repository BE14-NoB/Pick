package com.nob.pick.project.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberReviewDTO {
	private int id;		// 팀원 후기 번호
	private int rate; 	// 팀원 후기 평점
	private String content;		// 팀원 후기 내용
	private int reviewerId; 	// 후기 작성자
	private int reveiweeId; 	// 후기 대상자

}

package com.nob.pick.dailymission.command.domain.aggregate;

import com.nob.pick.common.config.BooleanToYNConverter;
import com.nob.pick.member.command.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="MEMBER_DAILY_MISSION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDailyMission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Convert(converter = BooleanToYNConverter.class) // 'Y'/'N' 변환
	@Column(name = "is_completed", nullable = false)
	private Boolean isCompleted = false;

	@Column(name = "accepted_date")
	private String acceptedDate;

	@ManyToOne
	@JoinColumn(name = "daily_mission_id", referencedColumnName = "id", nullable = false)
	private DailyMission dailyMission;

	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
	private Member member;
}

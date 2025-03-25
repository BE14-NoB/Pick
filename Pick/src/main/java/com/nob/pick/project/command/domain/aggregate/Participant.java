package com.nob.pick.project.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name="participant")
public class Participant {

	@Id
	@Column(name="id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="is_manager", nullable = false)
	@Builder.Default
	private boolean isManager = false;

	@Column(name="member_id", nullable = false)
	private int memberId;

	@Column(name="project_room_id", nullable = false)
	private int projectRoomId;

}

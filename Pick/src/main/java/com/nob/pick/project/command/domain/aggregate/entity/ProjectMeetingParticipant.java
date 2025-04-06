package com.nob.pick.project.command.domain.aggregate.entity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name="project_meeting_participant")
public class ProjectMeetingParticipant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable= false)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_id")
	private ProjectMeeting meeting;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "participant_id")
	private Participant participant;

	@Column(name="participant_type")
	private int participantType;

}

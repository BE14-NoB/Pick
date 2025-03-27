package com.nob.pick.project.command.application.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nob.pick.member.command.entity.Member;
import com.nob.pick.member.command.repository.MemberRepository;
import com.nob.pick.project.command.application.dto.RequestParticipantDTO;
import com.nob.pick.project.command.application.dto.RequestProjectRoomDTO;
import com.nob.pick.project.command.domain.aggregate.entity.Participant;
import com.nob.pick.project.command.domain.aggregate.entity.ProjectRoom;
import com.nob.pick.project.command.domain.repository.ParticipantRepository;
import com.nob.pick.project.command.domain.repository.ProjectRoomRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("CommandProjectRoomService")
public class ProjectRoomServiceImpl implements ProjectRoomService {
	private final ProjectRoomRepository projectRoomRepository;
	private final ParticipantRepository participantRepository;

	@Autowired
	public ProjectRoomServiceImpl(ProjectRoomRepository projectRoomRepository,
								  ParticipantRepository participantRepository) {
		this.projectRoomRepository = projectRoomRepository;
		this.participantRepository = participantRepository;
	}

	// 자율 매칭 방 생성
	@Override
	@Transactional
	public void createNonMatchingProject(RequestProjectRoomDTO newProjectRoom) {
		int durationMonth = parseDurationMonth(newProjectRoom.getDurationTime());
		LocalDate now = LocalDate.now();

		String durationTimeStr = durationMonth + "개월";
		String startDateStr = now.toString();
		String endDateStr = now.plusMonths(durationMonth).toString();

		int sessionCode = generateRangeRandomNum();
		log.info("생성된 세션 코드: {}", sessionCode);

		ProjectRoom projectRoom = ProjectRoom.builder()
			.name(newProjectRoom.getName())
			.content(newProjectRoom.getContent())
			.maximumParticipant(newProjectRoom.getMaximumParticipant())
			.durationTime(durationTimeStr)
			.startDate(LocalDate.parse(startDateStr))
			.endDate(LocalDate.parse(endDateStr))
			.sessionCode(sessionCode)
			.isFinished(false)
			.isDeleted(false)
			.thumbnailImage(null)
			.introduction(null)
			.thumbnailImage(null)
			.technologyCategoryId(newProjectRoom.getTechnologyCategory())
			.build();
		System.out.println("projectRoom = " + projectRoom);

		ProjectRoom savedProjectRoom = projectRoomRepository.save(projectRoom);
		// 확인
		log.info("Project Room 생성 완료! ID: {}", savedProjectRoom.getId());

		// 팀원 INSERT
		insertParticipants(newProjectRoom.getParticipantList(), savedProjectRoom);

	}

	// 매칭 방 생성 
	@Override
	@Transactional
	public void createMatchingProject(RequestProjectRoomDTO newProjectRoom) {
		int durationMonth = parseDurationMonth(newProjectRoom.getDurationTime());
		LocalDate now = LocalDate.now();

		String durationTimeStr = durationMonth + "개월";
		String startDateStr = now.toString();
		String endDateStr = now.plusMonths(durationMonth).toString();
		// 세션 코드 없음
		ProjectRoom projectRoom = ProjectRoom.builder()
			.name(newProjectRoom.getName())
			.content(newProjectRoom.getContent())
			.maximumParticipant(newProjectRoom.getMaximumParticipant())
			.durationTime(durationTimeStr)
			.startDate(LocalDate.parse(startDateStr))
			.endDate(LocalDate.parse(endDateStr))
			.isFinished(false)
			.isDeleted(false)
			.thumbnailImage(null)
			.introduction(null)
			.thumbnailImage(null)
			.technologyCategoryId(newProjectRoom.getTechnologyCategory())
			.build();
		System.out.println("projectRoom = " + projectRoom);

		ProjectRoom savedProjectRoom = projectRoomRepository.save(projectRoom);
		// 확인
		log.info("Project Room 생성 완료! ID: {}", savedProjectRoom.getId());

		// 팀원 INSERT
		insertParticipants(newProjectRoom.getParticipantList(), savedProjectRoom);

	}

	// 생성된 프로젝트 팀원 등록
	private void insertParticipants(List<RequestParticipantDTO> participantList, ProjectRoom projectRoom) {
		for(RequestParticipantDTO participant : participantList) {

			// 유효한 Member인지 확인

			// 팀원 정보 등록
			Participant newParticipant = Participant.builder()
				.memberId(participant.getMemberId())
				.projectRoom(projectRoom)
				.isManager(participant.isManager())
				.build();

			log.info("새로운 팀원 : {}",  newParticipant);

			Participant savedParticipant = participantRepository.save(newParticipant);

			log.info("팀원 객체 생성 완료! : {}", savedParticipant);
		}
	}

	// ## TODO
	// 매일 새벽 3시, 유예기간(일주일) 내 팀원 모집 실패한 자율 매칭 프로젝트 방 삭제
	@Scheduled(cron = "0 0 3 * * *")
	public void deleteUnmatchedProjectRooms(){
		// List<>
	}

	// 개발 기간 기반 프로젝트 마감 기간 계산
	private int parseDurationMonth(String durationTime) {
		String numberStr = durationTime.replaceAll("[^0-9]", "");

		if (numberStr.isEmpty()) {
			throw new IllegalArgumentException("유효한 개월 수가 없습니다: " + durationTime);
		}

		return Integer.parseInt(numberStr);
	}

	// 세션 코드용 6자리 랜덤 숫자 생성 메서드 		##
	public static int generateRangeRandomNum() {
		SecureRandom secureRandom = new SecureRandom();
		int start = 100000;
		int end = 999999;
		return start + secureRandom.nextInt(end - start + 1);
	}
}

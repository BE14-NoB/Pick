package com.nob.pick.project.command.application.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nob.pick.common.util.FileUploader;
import com.nob.pick.project.command.application.dto.ProjectRoomEditDTO;
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

	private final FileUploader fileUploader;
	private final ProjectRoomRepository projectRoomRepository;
	private final ParticipantRepository participantRepository;

	@Autowired
	public ProjectRoomServiceImpl(FileUploader fileUploader,
		  						  ProjectRoomRepository projectRoomRepository,
								  ParticipantRepository participantRepository) {
		this.fileUploader = fileUploader;
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
			.technologyCategoryId(newProjectRoom.getTechnologyCategory())
			.build();
		System.out.println("projectRoom = " + projectRoom);

		ProjectRoom savedProjectRoom = projectRoomRepository.save(projectRoom);
		// 확인
		log.info("자율 매칭 프로젝트 방 생성 완료! ID: {}", savedProjectRoom.getId());

		// 팀원 INSERT
		insertParticipants(newProjectRoom.getParticipantList(), savedProjectRoom);

	}

	// 프로젝트 방 정보 수정
	@Override
	@Transactional
	public void updateProject(int projectId, ProjectRoomEditDTO projectInfo, MultipartFile thumbnailFile, int memberId) {
		ProjectRoom project = projectRoomRepository.findById(projectId)
			.orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 없습니다."));

		// 팀원 여부 확인
		boolean isMember = participantRepository.existsByProjectRoomIdAndMemberId(projectId, memberId);
		if(!isMember){
			log.info("해당 프로젝트의 팀원 아님");
			throw new AccessDeniedException("프로젝트 수정 권한이 없습니다.");
		}

		//	텍스트 필드 업데이트
		project.updateInfo(projectInfo);

		// 썸네일 업데이트
		if(thumbnailFile != null && !thumbnailFile.isEmpty()) {
			String thumbnailPath = fileUploader.save(thumbnailFile);	// 썸네일 업로드
			project.setThumbnailUrl(thumbnailPath);              // DB에 경로 저장
		}

		projectRoomRepository.save(project);
	}

	// 입장 코드 입력 후 자율 매칭 프로젝트 방 입장
	@Override
	public void joinProjectRoom(int sessionCode, int memberId) {
		// 프로젝트 조회
		ProjectRoom projectRoom = (ProjectRoom) projectRoomRepository.findBySessionCode(sessionCode)
			.orElseThrow(() -> new IllegalArgumentException("해당 코드에 해당하는 프로젝트가 없습니다."));

		// 이미 참가한 팀원인지 확인
		boolean alreadyJoined = participantRepository.existsByProjectRoomIdAndMemberId(projectRoom.getId(), memberId);
		if(alreadyJoined){
			throw new IllegalStateException("이미 해당 프로젝트에 참가한 회원입니다.");
		}
		// 인원 초과 확인
		int currentCount = participantRepository.countByProjectRoomId(projectRoom.getId());
		if(currentCount >= projectRoom.getMaximumParticipant()){
			throw new IllegalStateException("프로젝트의 정원이 초과하였습니다.");
		}

		// 팀원으로 추가
		Participant newParticipant = Participant.builder()
			.memberId(memberId)
			.projectRoom(projectRoom)
			.isManager(false)
			.build();
		participantRepository.save(newParticipant);
	}

	// 생성된 프로젝트 팀원 자동 등록
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

	
	// 매일 새벽 00시, 유예기간(일주일) 내 팀원 모집에 실패한 자율 매칭 프로젝트 방 삭제
	@Scheduled(cron = "0 0 0 * * *")	// 매일 자정에 실행
	@Transactional
	public void deleteUnmatchedProjectRooms(){
		// 자율 매칭 방 (세션 코드가 존재)
		// 마감 전 : endDate > 오늘
		// 모집 미완료 상태 
		// 생성된 지 7일이 지남
		// 참여자 수가 목표치보다 적음

		LocalDate cutoffDate = LocalDate.now().minusDays(7);
		List<ProjectRoom> candidates = projectRoomRepository.findUnmatchedRoomsBefore(cutoffDate);

		for(ProjectRoom room : candidates) {
			int participantCount = participantRepository.countByProjectRoomId(room.getId());

			if(participantCount < room.getMaximumParticipant()) {
				room.setDeleted(true);
				log.info("자동 삭제된 자율 매칭 방 ID: {}, Name: {}", room.getId(), room.getName());
			}

		}
		projectRoomRepository.saveAll(candidates);

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

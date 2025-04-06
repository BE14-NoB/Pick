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

	// 랜덤 매칭 방 생성
	@Override
	@Transactional
	public void createMatchingProject(RequestProjectRoomDTO newProjectRoom) {
		// 방 이름 중복 방지
		String uniqueName = generateUniqueRoomName(newProjectRoom.getName());

		ProjectRoom projectRoom = buildProjectRoom(newProjectRoom, uniqueName, null);
		System.out.println("projectRoom = " + projectRoom);

		ProjectRoom saved = projectRoomRepository.save(projectRoom);
		log.info("랜덤 매칭 프로젝트 방 생성 완료! ID: {}", saved.getId());

		insertParticipants(newProjectRoom.getParticipantList(), saved);
	}

	// 자율 매칭 방 생성
	@Override
	@Transactional
	public void createNonMatchingProject(RequestProjectRoomDTO newProjectRoom) {
		// 방 이름 중복 방지
		String uniqueName = generateUniqueRoomName(newProjectRoom.getName());
		
		// 세션 코드 생성
		int sessionCode = generateUniqueSessionCode();
		log.info("생성된 세션 코드: {}", sessionCode);

		ProjectRoom projectRoom = buildProjectRoom(newProjectRoom, uniqueName, sessionCode);
		System.out.println("projectRoom = " + projectRoom);

		ProjectRoom saved = projectRoomRepository.save(projectRoom);
		log.info("자율 매칭 방 생성 완료! ID: {}", saved.getId());

		insertParticipants(newProjectRoom.getParticipantList(), saved);
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






	// 프로젝트 방 객체 생성
	private ProjectRoom buildProjectRoom(RequestProjectRoomDTO dto, String name, Integer sessionCode) {
		int durationMonth = parseDurationMonth(dto.getDurationTime());
		LocalDate now = LocalDate.now();

		return ProjectRoom.builder()
			.name(name)
			.content(dto.getContent())
			.maximumParticipant(dto.getMaximumParticipant())
			.durationTime(durationMonth + "개월")
			.startDate(now)
			.endDate(now.plusMonths(durationMonth))
			.sessionCode(sessionCode) // null이면 세션 없음
			.isFinished(false)
			.isDeleted(false)
			.thumbnailImage(null)
			.introduction(null)
			.technologyCategoryId(dto.getTechnologyCategory())
			.build();
	}


	// 생성된 프로젝트 팀원 자동 등록
	private void insertParticipants(List<RequestParticipantDTO> participantList, ProjectRoom projectRoom) {
		for(RequestParticipantDTO participant : participantList) {

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

	// 매일 새벽 00시, 유예기간(일주일) 내 팀원 모집에 실패한 모든 프로젝트 방 삭제
	@Scheduled(cron = "0 0 0 * * *")	// 매일 자정에 실행
	@Transactional
	public void deleteUnmatchedProjectRooms(){
		// 자율 매칭 방 (세션 코드가 존재)
		// 마감 전 : endDate > 오늘
		// 모집 미완료 상태 
		// 생성된 지 7일이 지남
		// 참여자 수가 목표치보다 적음

		LocalDate cutoffDate = LocalDate.now().minusDays(7);
		List<ProjectRoom> candidates = projectRoomRepository.findProjectRoomsBefore(cutoffDate);

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
	private int generateRangeRandomNum() {
		SecureRandom secureRandom = new SecureRandom();
		int start = 100000;
		int end = 999999;
		return start + secureRandom.nextInt(end - start + 1);
	}

	// 세션 코드 중복 제거
	private int generateUniqueSessionCode() {
		int sessionCode;
		do {
			sessionCode = generateRangeRandomNum();
		} while (projectRoomRepository.existsBySessionCode(sessionCode));
		return sessionCode;
	}

	// 방 이름 중복 시 리네임 메서드
	private String generateUniqueRoomName(String baseName) {
		List<String> existingNames = projectRoomRepository.findProjectRoomNamesStartingWith(baseName);

		// 기본 이름 그대로 사용 가능한 경우
		if(!existingNames.contains(baseName)){
			return baseName;
		}

		// 이름에 랜덤 코드 덧붙이기
		String randomCode = generateRandomAlphaNumeric(6); // 6자리 랜덤 코드
		return baseName + "_" + randomCode;
	}

	private String generateRandomAlphaNumeric(int length) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		return sb.toString();
	}
}

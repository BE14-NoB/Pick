package com.nob.pick.common.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploader {

	private final String basePath = "C:\\BackendProject\\Pick\\Pick\\src\\main\\java\\com\\nob\\pick\\common\\static\\uploads\\project\\thumbnail\\";

	public String save(MultipartFile file) {
		if(file.isEmpty()) {
			throw new IllegalArgumentException("빈 파일은 저장할 수 없습니다.");
		}

		String originalFilename = file.getOriginalFilename();
		String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
		String newFileName = UUID.randomUUID().toString() + ext;

		File directory = new File(basePath);
		if (!directory.exists()) {
			directory.mkdirs(); // 디렉토리 없으면 생성
		}
		File savedFile = new File(basePath + newFileName);

		try {
			file.transferTo(savedFile);
		} catch (IOException e) {
			throw new RuntimeException("파일 저장 실패: ", e);
		}

		// 클라이언트가 접근할 상대 경로 반환 (DB 저장용)
		return "/uploads/project/thumbnail/" + newFileName;

	}
}

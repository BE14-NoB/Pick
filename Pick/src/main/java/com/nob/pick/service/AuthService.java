package com.nob.pick.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

	public Map<String, Object> getCurrentUserInfo() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes == null) {
			throw new IllegalStateException("요청 컨텍스트가 없습니다.");
		}

		HttpServletRequest request = attributes.getRequest();
		String userId = request.getHeader("X-User-Id");
		String email = request.getHeader("X-User-Email");
		String roles = request.getHeader("X-User-Roles");

		if (userId == null || email == null || roles == null) {
			throw new IllegalStateException("사용자 정보가 누락되었습니다.");
		}

		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("id", Long.valueOf(userId));
		userInfo.put("email", email);
		userInfo.put("roles", Arrays.asList(roles.split(",")));
		return userInfo;
	}

	public void createRoom() {
		Map<String, Object> userInfo = getCurrentUserInfo();
		Long userId = (Long) userInfo.get("id");
		System.out.println("사용자 " + userId + "가 방을 생성했습니다.");
	}
}
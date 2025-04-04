package com.nob.kickmember.member.command.controller;

import com.nob.kickmember.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 사용자 정보를 제공하는 컨트롤러
 * pick 도메인에서 호출하여 인증된 사용자 정보를 가져감
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class UserInfoController {

	private final JwtUtil jwtUtil;

	@GetMapping("/user-info")
	public ResponseEntity<Map<String, Object>> getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
		try {
			String token = authorizationHeader.replace("Bearer ", "").trim();
			String email = jwtUtil.getEmail(token);
			List<String> roles = jwtUtil.getRoles(token);
			Long id = jwtUtil.getId(token);

			Map<String, Object> response = new HashMap<>();
			response.put("email", email);
			response.put("roles", roles);
			response.put("id", id);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(400)
				.body(Map.of("error", "사용자 정보 조회 중 오류: " + e.getMessage()));
		}
	}
}
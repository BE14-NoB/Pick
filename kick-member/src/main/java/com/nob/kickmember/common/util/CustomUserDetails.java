package com.nob.kickmember.common.util;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
// jwt 토큰에서 유저에 대한 정보 가져올 것들을 여기다 추가
public class CustomUserDetails extends User {
	private final Long id;  // 사용자 ID 추가

	public CustomUserDetails(Long id, String email, Collection<? extends GrantedAuthority> authorities) {
		super(email, "", authorities);  // 비밀번호는 사용하지 않으므로 빈 문자열 ""
		this.id = id;
	}
}

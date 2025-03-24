package com.nob.pick.common.util;

import com.nob.pick.member.command.entity.Member;
import com.nob.pick.member.command.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String password = (String) authentication.getCredentials();

		// 이메일로 회원 조회
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new AuthenticationException("유효하지 않은 이메일입니다.") {
				@Override
				public String getMessage() {
					return super.getMessage();
				}
			});

		// 비밀번호 검증
		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw new AuthenticationException("유효하지 않은 패스워드입니다.") {
				@Override
				public String getMessage() {
					return super.getMessage();
				}
			};
		}

		// 권한 설정
		List<SimpleGrantedAuthority> authorities = member.getRoles().stream()
			.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
			.collect(Collectors.toList());

		// 인증 객체 반환
		return new UsernamePasswordAuthenticationToken(email, null, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
package com.nob.pick.common.socket;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSocketMessageBroker		// STOMP 기반 WebSocket 메시지 브로커 기능을 활성화
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		
		// 서버 → 클라이언트로 메시지를 보내는 주소
		config.enableSimpleBroker("/topic");	// (브라우저가 구독하는 경로)
		
		// 클라이언트 -> 서버로 메시지를 보낼 때 사용하는 주소
		config.setApplicationDestinationPrefixes("/app");		// 전송 주소
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")		// 클라이언트 웹소켓 연결 엔드포인트
			.setAllowedOriginPatterns("*")			// CORS 허용 (테스트용)
			.withSockJS();						// Sock.js fallback 옵션(웹소켓 지원 되지 않는 브라우저용)
	}
}

package com.sparta.moit.global.config;

import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.global.error.CustomException;
import com.sparta.moit.global.error.ErrorCode;
import com.sparta.moit.global.jwt.JwtUtil;
import com.sparta.moit.global.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j(topic = "StompHandler log")
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompHeaderAccessor accessor = MessageHeaderAccessor
                .getAccessor(message, StompHeaderAccessor.class);
        // 연결 요청시 JWT 검증
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // Authorization 헤더 추출
            List<String> authorization = accessor.getNativeHeader("Authorization");
            if (authorization != null && !authorization.isEmpty()) {
                String jwt = authorization.get(0).substring(7);
                try {
                    // JWT 토큰 검증
                    Claims decodedJWT = jwtUtil.getUserInfoFromToken(jwt);
                    String email = decodedJWT.getSubject();
                    // 사용자 정보 조회
                    Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                            new CustomException(ErrorCode.NOT_EXIST_USER)
                    );

                    // 사용자 인증 정보 설정
                    UserDetailsImpl userDetails = new UserDetailsImpl(member);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    // accessor에 등록
                    accessor.setUser(authentication);
                } catch (RuntimeException e) {
                    log.error("JWT Verification Failed: " + e.getMessage());
                    return null;
                } catch (Exception e) {
                    log.error("An unexpected error occurred: " + e.getMessage());
                    return null;
                }
            } else {
                // 클라이언트 측 타임아웃 처리
                log.error("Authorization header is not found");
                return null;
            }
        }
        return message;
    }
}

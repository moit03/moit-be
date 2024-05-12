package com.sparta.moit.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moit.domain.member.entity.UserRoleEnum;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.global.common.repository.RedisRefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    private final MemberRepository memberRepository;

    private final RedisRefreshTokenRepository redisRefreshTokenRepository;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String BEARER_PREFIX = "Bearer ";
    private final long TOKEN_TIME = 24 * 60 * 60 * 1000L; // 1 day
    private final long REFRESH_TOKEN_TIME = 14 * 24 * 60 * 60 * 1000L; /*14일*/
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public JwtUtil(MemberRepository memberRepository, RedisRefreshTokenRepository redisRefreshTokenRepository) {
        this.memberRepository = memberRepository;
        this.redisRefreshTokenRepository = redisRefreshTokenRepository;
    }

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(String email, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email)
                        .claim("type", "access")
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token, HttpServletResponse res) throws IOException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            sendErrorResponse(res, HttpStatus.UNAUTHORIZED.value(), "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            return false;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            sendErrorResponse(res, HttpStatus.UNAUTHORIZED.value(), "Expired JWT token, 만료된 JWT token 입니다.");
            return false;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            sendErrorResponse(res, HttpStatus.UNAUTHORIZED.value(), "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            return false;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            sendErrorResponse(res, HttpStatus.UNAUTHORIZED.value(), "JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            return false;
        }
    }

    public String getTokenType(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey) // secretKey 설정
                .parseClaimsJws(token)   // 토큰 파싱 및 검증
                .getBody();             // 토큰의 body (claims) 가져오기

        return claims.get("type", String.class);
    }

    private void sendErrorResponse(HttpServletResponse res, int statusCode, String errorMessage) throws IOException {
        res.setCharacterEncoding("utf-8");
        res.setContentType("application/json");
        res.setStatus(statusCode);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(res.getWriter(), Map.of("key","UNAUTHORIZED","StatusCode", res.getStatus(),"message", errorMessage));
    }

    /*리프레시 토큰 생성 메서드*/
    public String createRefreshToken(String email, UserRoleEnum role) {

        Date now = new Date();

        String refresh = Jwts.builder()
                .setSubject(email) /*사용자 식별자값(ID)*/
                .claim(AUTHORIZATION_KEY, role) /*사용자 권한*/
                .claim("type", "refresh")
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_TIME)) /*만료 시간*/
                .setIssuedAt(now) /*발급일*/
                .signWith(key, signatureAlgorithm) /*암호화 알고리즘*/
                .compact();

//        RedisRefreshToken refreshToken = RedisRefreshToken.builder()
//                .token(refresh)
//                .email(email)
//                .build();
//
//        redisRefreshTokenRepository.save(refreshToken);

        return refresh;
    }

    public void addRefreshTokenToCookie(String refreshToken, HttpServletResponse response) {
        refreshToken = URLEncoder.encode(refreshToken, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        Cookie cookie = new Cookie("RefreshToken", refreshToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        cookie.setMaxAge((int) (REFRESH_TOKEN_TIME / 1000));

        response.addCookie(cookie);
    }

    public void addAccessTokenToCookie(String accessToken, HttpServletResponse response) {
        accessToken = URLEncoder.encode(accessToken, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        Cookie cookie = new Cookie("AccessToken", accessToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        cookie.setMaxAge((int) (TOKEN_TIME / 1000));

        response.addCookie(cookie);
        System.out.println(cookie);
    }

    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("RefreshToken")) {
                    log.info("RefreshToken cookie value: "+ URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8));
                    log.info("check----");
                    return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                }
            }
        }
        return null;
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
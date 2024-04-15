package com.sparta.moit.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moit.domain.member.dto.MemberResponseDto;
import com.sparta.moit.domain.member.dto.NaverUserInfoDto;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.entity.UserRoleEnum;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.global.error.CustomValidationException;
import com.sparta.moit.global.jwt.JwtUtil;
import com.sparta.moit.global.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Slf4j(topic = "Naver Login")
@Service
@RequiredArgsConstructor
public class NaverServiceImpl implements NaverService{
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final RefreshTokenService refreshTokenService;

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    @Value("${naver.redirect-uri}")
    private String redirectUri;

    public MemberResponseDto naverLogin(String code, String state) throws JsonProcessingException {
        String accessToken = getAccessToken(code,state);
        NaverUserInfoDto naverUserInfo = getNaverUserInfo(accessToken);
        Member naverMember = registerNaverUserIfNeeded(naverUserInfo);
        String createToken = jwtUtil.createToken(naverMember.getEmail(), naverMember.getRole());

        /* Refresh Token 생성 및 저장 */
        String refreshToken = jwtUtil.createRefreshToken(naverMember.getEmail(), naverMember.getRole());
        String refreshTokenValue = refreshTokenService.createAndSaveRefreshToken(naverMember.getEmail(), refreshToken);

        MemberResponseDto responseDto = MemberResponseDto.builder().username(naverMember.getUsername()).accessToken(createToken).refreshToken(refreshTokenValue).build();
        return responseDto;
    }

    private String getAccessToken(String code, String state) throws JsonProcessingException {
        /* Request URL 만들기 */
        URI uri = UriComponentsBuilder
                .fromUriString("https://nid.naver.com")
                .path("/oauth2.0/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .queryParam("state",state)
                .encode()
                .build()
                .toUri();

        /* HTTP Header 생성 */
        HttpHeaders headers = new HttpHeaders();

        /* HTTP Body 생성 */
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        /* HTTP request 보내기 */
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        /* Parse the JSON response to extract the access token */
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        log.info("jsonNode.get " + jsonNode.get("access_token").asText());
        String accessToken = jsonNode.get("access_token").asText();

        return accessToken;
    }

    private NaverUserInfoDto getNaverUserInfo(String accessToken) throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/nid/me")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        /* HTTP 요청 보내기 */
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        String id = jsonNode.get("response").get("id").asText();
        log.info(id);
        String name = jsonNode.get("response").get("name").asText();
        String email = jsonNode.get("response").get("email").asText();

        log.info("네이버 사용자 정보: " + id + ", " + name + ", " + email);
        return new NaverUserInfoDto(id, name, email);
    }

    private Member registerNaverUserIfNeeded(NaverUserInfoDto naverUserInfo) {

        /* DB 에 중복된 Naver Id 가 있는지 확인 */
        String naverId = naverUserInfo.getId();
        log.info("Naver ID: " + naverId);
        Member naverUser = memberRepository.findByNaverId(naverId).orElse(null);
        log.info("Existing Naver User: " + naverUser);

        if (naverUser == null) {
            String naverEmail = naverUserInfo.getEmail();
            Member sameEmailUser = memberRepository.findByEmail(naverEmail).orElse(null);
            if (sameEmailUser != null) {
                naverUser = sameEmailUser;
                /* 기존 회원정보에 Naver Id 추가 */
                naverUser = naverUser.updateNaverId(naverId);
                log.info("Naver Email: " + naverEmail);
            } else {
                /* 신규 회원가입 */
                /* password: random UUID */
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);
                naverUser = Member.builder()
                        .username(naverUserInfo.getName())
                        .password(encodedPassword)
                        .email(naverUserInfo.getEmail())
                        .role(UserRoleEnum.USER)
                        .naverId(naverId)
                        .build();
            }
            log.info("Final Naver User: " + naverUser);
            memberRepository.save(naverUser);
        }
        return naverUser;
    }
    /* refreshAccessToken */
    private String refreshAccessToken(String refreshToken) {
        Optional<String> newAccessToken = refreshTokenService.refreshAccessToken(refreshToken);
        return newAccessToken.orElseThrow(() -> new CustomValidationException("Failed to refresh access token", null));
    }

    /* 로그아웃 */
    public void logout(String refreshTokenString){
        refreshTokenService.deleteRefreshToken(refreshTokenString);
    }

    public String refreshToken(String refreshToken) {
        return refreshAccessToken(refreshToken);
    }

}

package com.sparta.moit.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moit.domain.member.dto.KakaoUserInfoDto;
import com.sparta.moit.domain.member.dto.MemberResponseDto;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.entity.UserRoleEnum;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.global.jwt.JwtUtil;
import com.sparta.moit.global.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Slf4j(topic = "Kakao Login")
@Service
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService{
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final RefreshTokenService refreshTokenService;

    public String login() {
        return jwtUtil.createToken("brandy0108@daum.net", UserRoleEnum.USER);
    }
    public MemberResponseDto kakaoLogin(String code) throws JsonProcessingException {
        /* 1. "인가 코드"로 "액세스 토큰" 요청 */
        String accessToken = getToken(code);

        /* 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기 */
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        /* 3. 필요시에 회원가입 */
        Member kakaoMember = registerKakaoUserIfNeeded(kakaoUserInfo);

        /* 4. JWT 토큰 반환 */
        /* 이게 우리서버 Access Token */
        String createToken = jwtUtil.createToken(kakaoMember.getEmail(), kakaoMember.getRole());

        /* 이게 우리서버 Refresh Token 발급 */
        String refreshToken = jwtUtil.createRefreshToken(kakaoMember.getEmail(), kakaoMember.getRole());

        /* 발급한 토큰을 DB에 저장하기 */
        String refreshTokenValue = refreshTokenService.createAndSaveRefreshToken(kakaoMember.getEmail(), refreshToken);

        MemberResponseDto responseDto = MemberResponseDto.builder().username(kakaoMember.getUsername()).accessToken(createToken).refreshToken(refreshTokenValue).build();
        return responseDto;
    }

    private String getToken(String code) throws JsonProcessingException {
        log.info("인가코드 : " + code);
        /* 요청 URL 만들기 */
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        /* HTTP Header 생성 */
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        /* HTTP Body 생성 */
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "74153e11b9bd504556db7210160ed19d");
        body.add("client_secret", "AMlNyEikZaIgEBW9KBz7uIVe86PO381g");
        body.add("redirect_uri", "http://my-hangterest.s3-website-us-east-1.amazonaws.com/login/kakao");
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        /* HTTP 요청 보내기 */
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        /* HTTP 응답 (JSON) -> 액세스 토큰 파싱 */
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        String accessToken = jsonNode.get("access_token").asText();

        /*  access token 반환 */
        return accessToken;
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        log.info("accessToken : " + accessToken);

        /* 요청 URL 만들기 */
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        /* HTTP Header 생성 */
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

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
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();

        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new KakaoUserInfoDto(id, nickname, email);
    }

    private Member registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        /* DB 에 중복된 Kakao Id 가 있는지 확인 */
        Long kakaoId = kakaoUserInfo.getId();
        Member kakaoUser = memberRepository.findByKakaoId(kakaoId).orElse(null);

        if (kakaoUser == null) {
            /* 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인 */
            String kakaoEmail = kakaoUserInfo.getEmail();
            Member sameEmailUser = memberRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;

                /* 기존 회원정보에 카카오 Id 추가 */
                kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);

            } else {
                /* 신규 회원가입 */
                /* password: random UUID */
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                /* email: kakao email */
                kakaoUser = Member.builder()
                        .username(kakaoUserInfo.getNickname())
                        .password(encodedPassword)
                        .email(kakaoUserInfo.getEmail())
                        .role(UserRoleEnum.USER)
                        .kakaoId(kakaoId)
                        .build();
            }

            memberRepository.save(kakaoUser);
        }
        return kakaoUser;
    }

    /* 로그아웃 */
    public void logout(String refreshTokenString){
        refreshTokenService.deleteRefreshToken(refreshTokenString);
    }

}

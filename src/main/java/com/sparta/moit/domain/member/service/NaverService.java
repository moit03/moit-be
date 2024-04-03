package com.sparta.moit.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moit.domain.member.dto.NaverUserInfoDto;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.entity.UserRoleEnum;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.global.jwt.JwtUtil;
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

@Slf4j(topic = "Naver Login")
@Service
@RequiredArgsConstructor
public class NaverService {
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    public String naverLogin(String code, String state) throws JsonProcessingException {
        String accessToken = getAccessToken(code,state);
        NaverUserInfoDto naverUserInfo = getNaverUserInfo(accessToken);
        Member naverMember = registerNaverUserIfNeeded(naverUserInfo);
        String createToken = jwtUtil.createToken(naverMember.getEmail(), naverMember.getRole());
        return createToken;
    }

    private String getAccessToken(String code, String state) throws JsonProcessingException {
        // Request URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://nid.naver.com")
                .path("/oauth2.0/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", "pCzgB5uI4k0mvelBfCRz")
                .queryParam("client_secret", "0_eecfIk5d")
                .queryParam("redirect_uri", "http://my-hangterest.s3-website-us-east-1.amazonaws.com/login/naver")
                // http://my-hangterest.s3-website-us-east-1.amazonaws.com/login/naver
                // http://localhost:5173/login/naver
                .queryParam("code", code)
                .queryParam("state",state)
                .encode()
                .build()
                .toUri();

         // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        //headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("grant_type", "authorization_code");
//        body.add("client_id", "LeIb2VY5WfTDHHNTQmzN");
//        body.add("client_secret", "WHdVWARrEo");
//        //body.add("redirect_uri", "http://localhost:8080/api/member/signin/naver");
//        body.add("redirect_uri", "http://localhost:5173/login/naver");
//        body.add("code", code);
//        body.add("state", state);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        // HTTP request 보내기
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        // Parse the JSON response to extract the access token
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        log.info(jsonNode.get("access_token").asText());
        return jsonNode.get("access_token").asText();
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
        //headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("response").get("id").asLong();
        String name = jsonNode.get("response").get("name").asText();
        String email = jsonNode.get("response").get("email").asText();

        log.info("네이버 사용자 정보: " + id + ", " + name + ", " + email);
        return new NaverUserInfoDto(id, name, email);
    }

//    ResponseEntity<String> ResponseEntity = restTemplate.getForEntity(uri, String.class);
//    NaverUserInfoDto naverUserInfoDto = new ObjectMapper()
//            .readValue(ResponseEntity.getBody(), NaverUserInfoDto.class);
//        return naverUserInfoDto;
//}

    private Member registerNaverUserIfNeeded(NaverUserInfoDto naverUserInfo) {
        // DB 에 중복된 Naver Id 가 있는지 확인
        Long naverId = naverUserInfo.getId();
        Member naverUser = memberRepository.findByNaverId(naverId).orElse(null);

        if (naverUser == null) {
            String naverEmail = naverUserInfo.getEmail();
            Member sameEmailUser = memberRepository.findByEmail(naverEmail).orElse(null);
            if (sameEmailUser != null) {
                naverUser = sameEmailUser;
                // 기존 회원정보에 Naver Id 추가
                naverUser = naverUser.updateNaverId(naverId);
            } else {
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);
                naverUser = new Member(naverUserInfo.getName(), encodedPassword, naverUserInfo.getEmail(), UserRoleEnum.USER, naverId);
            }
            memberRepository.save(naverUser);
        }
        return naverUser;
    }
}
package com.sparta.moit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.entity.UserRoleEnum;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.domain.member.service.NaverService;
import com.sparta.moit.global.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NaverServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private NaverService naverService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNaverLogin() throws JsonProcessingException {
        // Given
        String code = "test_code";
        String state = "test_state";
        String accessToken = "test_access_token";
        String username = "test_username";

        Member naverMember = Mockito.mock(Member.class);
        Mockito.when(naverMember.getUsername()).thenReturn(username);
        Mockito.when(naverMember.getRole()).thenReturn(UserRoleEnum.USER);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("{\"access_token\": \"test_access_token\"}", headers, HttpStatus.OK);

        when(restTemplate.exchange(any(RequestEntity.class), eq(String.class))).thenReturn(responseEntity);
        when(jwtUtil.createToken(eq(username), eq(UserRoleEnum.USER))).thenReturn("test_token");

        // When
        String token = naverService.naverLogin(code, state);

        // Then
        assertEquals("test_token", token);
    }

    @Test
    public void testGetAccessToken() throws Exception {

        String code = "test_code";
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        MemberRepository memberRepository = mock(MemberRepository.class);

        NaverService naverService = new NaverService(passwordEncoder, restTemplate, jwtUtil, memberRepository);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("{\"access_token\": \"test_access_token\"}", headers, HttpStatus.OK);
        when(restTemplate.exchange(any(), eq(String.class))).thenReturn(responseEntity);

        Method method = NaverService.class.getDeclaredMethod("getAccessToken", String.class);
        method.setAccessible(true);

        // When
        String accessToken = (String) method.invoke(naverService, code);

        // Then
        assertEquals("test_access_token", accessToken);
    }
}

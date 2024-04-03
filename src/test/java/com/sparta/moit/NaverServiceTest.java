package com.sparta.moit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.moit.domain.member.dto.NaverUserInfoDto;
import com.sparta.moit.domain.member.entity.Member;
import com.sparta.moit.domain.member.entity.UserRoleEnum;
import com.sparta.moit.domain.member.repository.MemberRepository;
import com.sparta.moit.global.jwt.JwtUtil;
import com.sparta.moit.global.service.RefreshTokenService;
import com.sparta.moit.domain.member.service.NaverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NaverServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private NaverService naverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void naverLogin_ShouldReturnToken_WhenValidCodeAndStateProvided() throws JsonProcessingException {
//        // Given
//        String code = "mockCode";
//        String state = "mockState";
//        String refreshToken = "mockRefreshToken";
//        String accessToken = "mockAccessToken";
//        String email = "mockEmail@example.com";
//        String username = "mockUser";
//        Long naverId = 123L;
//        Member mockMember = new Member(username, "mockEncodedPassword", email, UserRoleEnum.USER, naverId);
//        NaverUserInfoDto naverUserInfoDto = new NaverUserInfoDto(naverId, username, email);
//
//        doNothing().when(refreshTokenService).createAndSaveRefreshToken(email, accessToken);
//
//        doReturn("mockToken").when(jwtUtil).createToken(username, UserRoleEnum.USER);
//        doReturn(new ResponseEntity<>(createNaverAccessTokenResponseJson(accessToken, refreshToken), HttpStatus.OK))
//                .when(restTemplate).exchange(any(RequestEntity.class), eq(String.class));
//        doReturn(new ResponseEntity<>(createNaverUserInfoResponseJson(naverId, username, email), HttpStatus.OK))
//                .when(restTemplate).exchange(any(RequestEntity.class), eq(String.class));
//        doReturn(Optional.empty()).when(memberRepository).findByNaverId(naverId);
//        doReturn(Optional.empty()).when(memberRepository).findByEmail(email);
//        doReturn("mockEncodedPassword").when(passwordEncoder).encode(anyString());
//        // When
//        String token = naverService.naverLogin(code, state, refreshToken);
//
//        // Then
//        assertNotNull(token);
//        assertEquals("mockToken", token);
//        verify(refreshTokenService, times(1)).createAndSaveRefreshToken(email, accessToken);
//        verify(jwtUtil, times(1)).createToken(username, UserRoleEnum.USER);
//        verify(memberRepository, times(1)).findByNaverId(naverId);
//        verify(memberRepository, times(1)).findByEmail(email);
//        verify(memberRepository, times(1)).save(mockMember);
//    }

    @Test
    void refreshToken_ShouldReturnNewAccessToken_WhenValidRefreshTokenProvided() {
        // Given
        String refreshToken = "mockRefreshToken";
        String newAccessToken = "mockNewAccessToken";

        when(refreshTokenService.refreshAccessToken(refreshToken)).thenReturn(Optional.of(newAccessToken));

        // When
        String accessToken = naverService.refreshToken(refreshToken);

        // Then
        assertEquals(newAccessToken, accessToken);
    }

    @Test
    void naverLogout_ShouldDeleteRefreshToken_WhenValidRefreshTokenProvided() {
        // Given
        String refreshToken = "mockRefreshToken";

        // When
        naverService.logout(refreshToken);

        // Then
        verify(refreshTokenService, times(1)).deleteRefreshToken(refreshToken);
    }

    private String createNaverAccessTokenResponseJson(String accessToken, String refreshToken) {
        return "{\"access_token\": \"" + accessToken + "\", \"refresh_token\": \"" + refreshToken + "\"}";
    }

    private String createNaverUserInfoResponseJson(Long id, String name, String email) {
        return "{\"response\": {\"id\": " + id + ", \"name\": \"" + name + "\", \"email\": \"" + email + "\"}}";
    }
}

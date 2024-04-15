//package com.sparta.moit;
//
//import com.sparta.moit.domain.member.entity.UserRoleEnum;
//import com.sparta.moit.global.common.entity.RedisRefreshToken;
//import com.sparta.moit.global.jwt.JwtUtil;
//import com.sparta.moit.global.repository.RedisRefreshTokenRepository;
//import com.sparta.moit.global.service.RedisService;
//import com.sparta.moit.global.service.RefreshTokenService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import java.util.Date;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class RefreshTokenServiceTest {
//
//    @Mock
//    private RedisService redisService;
//
//    @Mock
//    private JwtUtil jwtUtil;
//
//    @Mock
//    private RedisRefreshTokenRepository redisRefreshTokenRepository;
//
//    @InjectMocks
//    private RefreshTokenService refreshTokenService;
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testCreateAndSaveRefreshToken() {
//        String email = "test@example.com";
//        String refreshTokenString = "testRefreshToken";
//
//        RedisRefreshToken mockRedisRefreshToken = mock(RedisRefreshToken.class);
//        when(mockRedisRefreshToken.getToken()).thenReturn(refreshTokenString);
//        when(mockRedisRefreshToken.getEmail()).thenReturn(email);
//        when(mockRedisRefreshToken.getExpiryDate()).thenReturn(new Date());
//
//        when(redisRefreshTokenRepository.findByToken(anyString()))
//                .thenReturn(Optional.of(mockRedisRefreshToken));
//
//        String token = refreshTokenService.createAndSaveRefreshToken(email, refreshTokenString);
//        assertNotNull(token);
//        assertEquals(refreshTokenString, token);
//    }
//    @Test
//    public void testValidateRefreshToken() {
//        String token = "testToken";
//        Date expiryDate = new Date(System.currentTimeMillis() + 3600000L); // 1h
//        RedisRefreshToken refreshToken = new RedisRefreshToken("testToken", "test@example.com", expiryDate);
//
//        when(redisService.findRefreshToken(token)).thenReturn(Optional.of(refreshToken));
//
//        boolean isValid = refreshTokenService.validateRefreshToken(token);
//
//        assertTrue(isValid);
//    }
//
//    @Test
//    public void testRefreshAccessToken() {
//        String refreshTokenString = "testRefreshToken";
//        String email = "test@example.com";
//        String accessToken = "testAccessToken";
//
//        RedisRefreshToken refreshToken = new RedisRefreshToken(refreshTokenString, email, new Date(System.currentTimeMillis() + 3600000L)); // 1 hour from now
//
//        when(redisRefreshTokenRepository.findByToken(refreshTokenString)).thenReturn(Optional.of(refreshToken));
//        when(jwtUtil.createToken(email, UserRoleEnum.USER)).thenReturn(accessToken);
//
//        Optional<String> newAccessToken = refreshTokenService.refreshAccessToken(refreshTokenString);
//
//        assertTrue(newAccessToken.isPresent());
//        assertEquals(accessToken, newAccessToken.get());
//    }
//
//    @Test
//    public void testDeleteRefreshToken() {
//        String refreshTokenString = "testRefreshToken";
//
//        RedisRefreshToken refreshToken = new RedisRefreshToken(refreshTokenString, "test@example.com", new Date());
//
//        when(redisRefreshTokenRepository.findByToken(refreshTokenString)).thenReturn(Optional.of(refreshToken));
//
//        assertDoesNotThrow(() -> refreshTokenService.deleteRefreshToken(refreshTokenString));
//    }
//}
package com.sparta.moit.global.config;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@RequiredArgsConstructor
/*이 클래스가 Spring의 설정 클래스임을 나타냄*/
@Configuration
/*Redis 저장소를 위한 Spring Data Redis 저장소를 활성화*/
@EnableRedisRepositories
public class RedisConfig {

    /* Redis를 사용하여 데이터를 저장하고 조회하기 위한 기본환경 설정
    * 1. RedisTemplate은 Redis에 데이터를 저장하고 조회할 수 있는 기본적인 API를 제공
    * 2. RefreshToken 타입의 데이터를 저장하기 위한 별도의 RedisTemplate도 설정되어 있음.*/

    private final RedisProperties redisProperties;

    /*LettuceConnectionFactory 생성, Properties에서 가져온 호스트와 포트 정보를 사용하여 Redis에 연결*/
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

    /* serializer 설정으로 redis-cli를 통해 직접 데이터를 조회할 수 있도록 설정
     * 1. 기본 RedisTemplate을 생성
     * 2. 키와 값의 Serializer를 설정
     * 3. StringRedisSerializer: 문자열 키와 값의 직렬화를 위한 Serializer
     * 4. redisConnectionFactory()에서 생성한 연결 팩토리를 설정*/
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        return redisTemplate;
    }

    /* serializer 설정으로 redis-cli를 통해 직접 데이터를 조회할 수 있도록 설정
     * 1. RefreshToken 객체를 값으로 가지는 RedisTemplate을 생성
     * 2. 키는 여전히 문자열로, 값은 RefreshToken 객체로 직렬화
     * 3. Jackson2JsonRedisSerializer: Jackson 라이브러리를 사용하여 JSON 형태로 객체를 직렬화
     * 4. redisConnectionFactory()에서 생성한 연결 팩토리를 설정. */
    @Bean
    public RedisTemplate<String, RefreshToken> refreshTokenRedisTemplate() {
        RedisTemplate<String, RefreshToken> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(RefreshToken.class));
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        return redisTemplate;
    }
}
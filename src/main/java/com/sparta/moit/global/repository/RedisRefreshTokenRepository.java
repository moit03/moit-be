package com.sparta.moit.global.repository;

import com.sparta.moit.global.common.entity.RedisRefreshToken;
import org.apache.el.stream.Stream;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisRefreshTokenRepository extends CrudRepository<RedisRefreshToken, String> {
    Optional<RedisRefreshToken> findByToken(String token);
}

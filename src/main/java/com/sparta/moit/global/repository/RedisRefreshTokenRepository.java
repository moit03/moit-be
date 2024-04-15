package com.sparta.moit.global.repository;

import com.sparta.moit.global.common.entity.RedisRefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RedisRefreshTokenRepository extends CrudRepository<RedisRefreshToken, String> {
}

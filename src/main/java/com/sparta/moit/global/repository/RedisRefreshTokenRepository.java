package com.sparta.moit.global.repository;

import com.sparta.moit.global.common.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RedisRefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}

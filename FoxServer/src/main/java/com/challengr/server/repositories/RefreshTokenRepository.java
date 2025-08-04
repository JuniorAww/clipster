package com.challengr.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RefreshTokenRepository {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RefreshTokenRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void storeRefreshToken(Long userId, String refreshToken, Duration duration) {
        redisTemplate.opsForValue().set("refresh:" + userId, refreshToken, duration);
    }

    public boolean validateRefreshToken(String userId, String token) {
        String stored = redisTemplate.opsForValue().get("refresh:" + userId);
        return stored != null && stored.equals(token);
    }

    public void deleteRefreshToken(String userId) {
        redisTemplate.delete("refresh:" + userId);
    }
}

package com.malgn.configure.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setValues(String key, String value, Duration duration) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, value, duration);
    }

    public String getValue(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        if (values.get(key) == null) return "";
        return String.valueOf(values.get(key));
    }

    public void increaseValue(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.increment(key);
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    // 만료 시간 없는 저장 메서드 추가 (조회수용)
    public void setValues(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public List<Object> getMultiValues(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }
}
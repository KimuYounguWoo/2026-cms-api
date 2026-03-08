package com.malgn.api.content.service;

import com.malgn.configure.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ViewCountScheduler {

    private final RedisUtil redisUtil;
    private final JdbcTemplate jdbcTemplate;
    private static final String VIEW_CNT_PREFIX = "contentViewCnt:";

    @Scheduled(cron = "0 0 * * * *") // 매 시 정각 실행
    @Transactional
    public void syncViewCountToDb() {
        log.info(">>> [Schedule] Redis -> DB 조회수 동기화 시작");

        // 1. "contentViewCnt:*" 패턴의 모든 키 조회
        Set<String> keys = redisUtil.getKeys(VIEW_CNT_PREFIX + "*");
        if (keys == null || keys.isEmpty()) return;

        List<Object[]> batchArgs = new ArrayList<>();
        List<String> processedKeys = new ArrayList<>();

        for (String key : keys) {
            String value = redisUtil.getValue(key);
            if (!value.isEmpty()) {
                try {
                    Long contentId = Long.parseLong(key.substring(VIEW_CNT_PREFIX.length()));
                    Long viewCount = Long.valueOf(value);

                    batchArgs.add(new Object[]{viewCount, contentId});
                    processedKeys.add(key);
                } catch (Exception e) {
                    log.error(">>> 데이터 파싱 오류 [Key: {}]", key);
                }
            }
        }

        if (!batchArgs.isEmpty()) {
            // 2. 벌크 업데이트 수행 (Write-Behind)
            String sql = "UPDATE content SET view_count = ? WHERE id = ?";
            jdbcTemplate.batchUpdate(sql, batchArgs);

            // 3. DB 저장 완료 후 Redis 키 삭제
            // 다음 조회 시 다시 DB에서 최신값을 읽어 Redis를 초기화하도록 유도
            for (String key : processedKeys) {
                redisUtil.deleteValue(key);
            }
            log.info(">>> [Schedule] {}건의 데이터 DB 동기화 완료", batchArgs.size());
        }
    }
}
package com.chosu.jobssimpleboard.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@Builder
@RedisHash(value = "BoardArticleDto", timeToLive = 300)
@ToString
public class BoardListRedisDto {
    @Id
    private Long id;
    private String title;
    private String userId;
    private int hitCnt;
    private LocalDateTime createTime;
}

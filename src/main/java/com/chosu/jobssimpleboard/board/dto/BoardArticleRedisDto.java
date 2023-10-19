package com.chosu.jobssimpleboard.board.dto;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@RedisHash(value = "BoardArticleDto", timeToLive = 300)
public class BoardArticleRedisDto {
    @Id
    private Long id;
    private String title;
    private String contents;
    private String userId;
    private String localIp;
    private int hitCnt;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public BoardArticleRedisDto(Long id, String title, String contents, String userId, String localIp, int hitCnt, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.userId = userId;
        this.localIp = localIp;
        this.hitCnt = hitCnt;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}

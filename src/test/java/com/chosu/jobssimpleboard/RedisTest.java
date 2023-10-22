package com.chosu.jobssimpleboard;

import com.chosu.jobssimpleboard.board.dto.BoardArticleDto;
import com.chosu.jobssimpleboard.board.repository.BoardArticleDtoRepository;
import com.chosu.jobssimpleboard.board.repository.RedisBaseRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource(properties = { "spring.config.location=classpath:application-aws.yml" })
@AutoConfigureMockMvc
public class RedisTest {

    @Autowired
    RedisBaseRepository redisBaseRepository;

    @Autowired
    BoardArticleDtoRepository boardArticleDtoRepository;

    @DisplayName("1. redis get/set test")
    @Test
    void test_1(){
        redisBaseRepository.setValues("test", "test data");

        String test = redisBaseRepository.getValues("test");

        System.out.println("시스템로그 [test ]: " + test);

        Assertions.assertThat(test).isEqualTo("test data");
    }


    @DisplayName("2. redis get test ")
    @Test
    void test_2(){
        String test = redisBaseRepository.getValues("test");

        System.out.println("시스템로그 [test ]: " + test);
        Assertions.assertThat(test).isEqualTo("test data");
    }


    @DisplayName("3. view count logic")
    @Test
    void test_3(){
        BoardArticleDto boardArticleDto = boardArticleDtoRepository.save(BoardArticleDto.builder()
                        .viewCount(0)
                        .localIp("127.0.0.1")
                        .userId("user")
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .title("test")
                        .contents("test")
                        .build());

        boardArticleDtoRepository.findById(boardArticleDto.getId());

        redisBaseRepository.setValues("viewCount_"+ boardArticleDto.getId()
                , String.valueOf(boardArticleDto.getViewCount()+1));

        String viewCount = redisBaseRepository.getValues("viewCount_" + boardArticleDto.getId());

        System.out.println("시스템로그 [viewCount]: " + viewCount);
        Assertions.assertThat(viewCount).isEqualTo("1");
    }
}

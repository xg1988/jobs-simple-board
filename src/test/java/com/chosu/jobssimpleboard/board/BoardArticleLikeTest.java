package com.chosu.jobssimpleboard.board;

import com.chosu.jobssimpleboard.board.dto.BoardArticleLikeDto;
import com.chosu.jobssimpleboard.board.repository.BoardArticleDtoRepository;
import com.chosu.jobssimpleboard.board.repository.BoardArticleLikeDtoRepository;
import com.chosu.jobssimpleboard.board.repository.UserDtoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource(properties = { "spring.config.location=classpath:application-aws.yml" })
@AutoConfigureMockMvc
public class BoardArticleLikeTest {

    @Autowired
    UserDtoRepository userDtoRepository;

    @Autowired
    BoardArticleDtoRepository boardArticleDtoRepository;

    @Autowired
    BoardArticleLikeDtoRepository boardArticleLikeDtoRepository;

    @DisplayName("1. board article like test")
    @Test
    void test_1(){

        BoardArticleLikeDto boardArticleLikeDto = BoardArticleLikeDto.builder()
                .userDto(userDtoRepository.findById("user").orElseThrow())
                .boardArticleDto(boardArticleDtoRepository.findById(1L).orElseThrow())
                .updateTime(LocalDateTime.now())
                .createTime(LocalDateTime.now())
                .likeYn("Y") // default Y
                .build();

        BoardArticleLikeDto boardArticleLikeDto1 = boardArticleLikeDtoRepository.save(boardArticleLikeDto);

        System.out.println("시스템로그 [boardArticleLikeDto1]: " + boardArticleLikeDto1.toString());

        System.out.println("시스템로그 [boardArticleLikeDto1.getBoardArticleDto().getTitle()]: " + boardArticleLikeDto1.getBoardArticleDto().getTitle());

        Assertions.assertThat(boardArticleLikeDto1.getBoardArticleDto().getTitle()).isEqualTo("test");
    }
}

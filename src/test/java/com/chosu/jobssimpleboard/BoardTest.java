package com.chosu.jobssimpleboard;

import com.chosu.jobssimpleboard.board.dto.BoardArticleDto;
import com.chosu.jobssimpleboard.board.dto.BoardWriteDto;
import com.chosu.jobssimpleboard.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class BoardTest {


    @Autowired
    BoardService boardService;

    @DisplayName("1. 게시글 조회")
    @Test
    void test_1(){
        for (int i=0; i< 10; i++){
            BoardWriteDto boardWriteDto = BoardWriteDto.builder()
                    .title("테스트 제목 _" + i )
                    .contents("테스트 게시글")
                    .build();
            boardService.saveBoard(boardWriteDto.getTitle(), boardWriteDto.getContents(), "user", "127.0.0.1");
        }


        List<BoardArticleDto> list = boardService.selectList(10, 1);

        for(BoardArticleDto o: list){
            System.out.println("시스템로그 [o.toString()]: " + o.toString());
        }
    }
}

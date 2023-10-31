package com.chosu.jobssimpleboard.board.service;


import com.chosu.jobssimpleboard.board.dto.BoardCommentDto;
import com.chosu.jobssimpleboard.board.dto.BoardCommentWriteDto;
import com.chosu.jobssimpleboard.board.repository.BoardCommentDtoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class BoardCommentService {

    private final BoardCommentDtoRepository boardCommentDtoRepository;

    BoardCommentService(BoardCommentDtoRepository boardCommentDtoRepository){
        this.boardCommentDtoRepository = boardCommentDtoRepository;
    }

    public List<BoardCommentDto> getComments(Long boardId){
        log.info("boardId >> {}", boardId);

        return boardCommentDtoRepository.findByBoardIdOrderByIdDesc(boardId);
    }

    public BoardCommentDto insertComment(BoardCommentWriteDto boardCommentWriteDto, Principal principal){
        log.info("boardCommentWriteDto >> {}" , boardCommentWriteDto);

        Long boardId = boardCommentWriteDto.getBoardId();
        log.info("boardId >> {}" , boardId);

        BoardCommentDto boardCommentDto = BoardCommentDto.builder()
                .comment(boardCommentWriteDto.getComment())
                .boardTypeId(1L)
                .localIp("127.0.0.1")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .userId(principal.getName())
                .boardId(boardCommentWriteDto.getBoardId())
                .build();

        boardCommentDtoRepository.saveAndFlush(boardCommentDto);

        return boardCommentDto;
    }

}

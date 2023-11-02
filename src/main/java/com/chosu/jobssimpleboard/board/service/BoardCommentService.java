package com.chosu.jobssimpleboard.board.service;


import com.chosu.jobssimpleboard.board.dto.BoardCommentDto;
import com.chosu.jobssimpleboard.board.dto.BoardCommentWriteDto;
import com.chosu.jobssimpleboard.board.repository.BoardCommentDtoRepository;
import com.chosu.jobssimpleboard.kafka.KafkaCustomProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class BoardCommentService {

    private final BoardCommentDtoRepository boardCommentDtoRepository;
    private final KafkaCustomProducer kafkaCustomProducer;

    BoardCommentService(BoardCommentDtoRepository boardCommentDtoRepository, KafkaCustomProducer kafkaCustomProducer){
        this.boardCommentDtoRepository = boardCommentDtoRepository;
        this.kafkaCustomProducer = kafkaCustomProducer;
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
                .localIp(boardCommentWriteDto.getLocalIp())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .userId(principal.getName())
                .boardId(boardCommentWriteDto.getBoardId())
                .build();



        BoardCommentDto boardCommentResultDto = boardCommentDtoRepository.saveAndFlush(boardCommentDto);
        kafkaCustomProducer.send("insertComment_"
                + boardCommentResultDto.getBoardId()
                + "_" +boardCommentResultDto.getId()
                + "_" + boardCommentResultDto.getComment());

        return boardCommentDto;
    }

    @Transactional
    public void deleteComment(Long boardId, Long id, Principal principal) throws Exception {
        String userId = principal.getName();
        log.info("session userId >> {}" , userId);

        BoardCommentDto boardCommentDto = boardCommentDtoRepository.findByBoardIdAndIdAndUserId(boardId, id, userId);

        if(boardCommentDto == null){
            throw new Exception("댓글을 삭제할 수 없습니다.");
        }

        boardCommentDtoRepository.deleteByBoardIdAndId(boardId, id);
    }
}

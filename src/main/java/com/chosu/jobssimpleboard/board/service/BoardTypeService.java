package com.chosu.jobssimpleboard.board.service;

import com.chosu.jobssimpleboard.board.dto.BoardTypeDto;
import com.chosu.jobssimpleboard.board.dto.BoardTypeWriteDto;
import com.chosu.jobssimpleboard.board.repository.BoardTypeDtoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardTypeService {
    private final BoardTypeDtoRepository boardTypeDtoRepository;

    public List<BoardTypeDto> selectList(){
        return boardTypeDtoRepository.findAll();
    }

    @Transactional
    public BoardTypeDto insertType(BoardTypeWriteDto boardTypeWriteDto){

        BoardTypeDto boardTypeDto = boardTypeDtoRepository.saveAndFlush(BoardTypeDto.builder()
                .updateTime(LocalDateTime.now())
                .createTime(LocalDateTime.now())
                .boardTypeDesc(boardTypeWriteDto.getBoardTypeDesc())
                .id(boardTypeWriteDto.getBoardTypeId())
                .build());
        log.info("boardTypeDto >> {}", boardTypeDto);
        return boardTypeDto;
    }

    @Transactional
    public void deleteType(String typeId) {
        boardTypeDtoRepository.deleteById(Long.parseLong(typeId));
    }
}

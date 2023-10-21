package com.chosu.jobssimpleboard.board.service;


import com.chosu.jobssimpleboard.board.dto.BoardArticleDto;
import com.chosu.jobssimpleboard.board.dto.BoardArticleRedisDto;
import com.chosu.jobssimpleboard.board.dto.BoardListRedisDto;
import com.chosu.jobssimpleboard.board.dto.BoardModifyDto;
import com.chosu.jobssimpleboard.board.repository.BoardArticleDtoRepository;
import com.chosu.jobssimpleboard.board.repository.BoardListRedisDtoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BoardService {

    private BoardArticleDtoRepository boardArticleDtoRepository;
    private BoardListRedisDtoRepository boardListRedisDtoRepository;
    public Page<BoardArticleDto> selectList(Pageable pageable){

        log.info("selectList size >> {}", pageable.getPageSize());
        log.info("selectList page >> {}", pageable.getPageNumber());

        Page<BoardArticleDto> list =boardArticleDtoRepository.findAllByOrderByIdDesc(pageable);
        return list;
    }

    public void saveBoard(String title, String content, String username, String localIp) {

        BoardArticleDto boardArticleDto = boardArticleDtoRepository.saveAndFlush(BoardArticleDto.builder()
                        .contents(content)
                        .viewCount(0)
                        .title(title)
                        .localIp(localIp)
                        .userId(username)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                .build());
/*
        boardListRedisDtoRepository.save(BoardListRedisDto.builder()
                        .createTime(LocalDateTime.now())
                        .userId(username)
                        .hitCnt(0)
                        .title(title)
                        .id(boardArticleDto.getId())
                .build());*/
    }

    @Transactional
    public BoardArticleDto select(Long id) {
        /*Optional<BoardListRedisDto> boardListRedisDto = boardListRedisDtoRepository.findById(id);
        log.info("boardListRedisDto >> {}", boardListRedisDto);*/

        boardArticleDtoRepository.updateViewCount(id);

        return boardArticleDtoRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public void modifyBoard(BoardModifyDto boardModifyDto) {

        BoardArticleDto boardArticleDto = select(boardModifyDto.getId());

        log.info("boardArticleDto >> {}" , boardArticleDto);
        log.info("boardModifyDto >> {}" , boardModifyDto);

        boardArticleDtoRepository.saveAndFlush(BoardArticleDto.builder()
                .contents(boardModifyDto.getContents())
                .title(boardModifyDto.getTitle())
                .id(boardModifyDto.getId())
                .updateTime(LocalDateTime.now())

                .createTime(boardArticleDto.getCreateTime())
                .viewCount(boardArticleDto.getViewCount())
                .userId(boardArticleDto.getUserId())
                .localIp(boardArticleDto.getLocalIp())
                .build());
    }

    public void delete(Long id) {
        boardArticleDtoRepository.deleteById(id);
    }
}

package com.chosu.jobssimpleboard.board.service;


import com.chosu.jobssimpleboard.board.dto.BoardArticleDto;
import com.chosu.jobssimpleboard.board.repository.BoardArticleDtoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BoardService {

    private BoardArticleDtoRepository boardArticleDtoRepository;

    public List<BoardArticleDto> selectList(Pageable pageable){
        log.info("selectList size >> {}", pageable.getPageSize());
        log.info("selectList page >> {}", pageable.getPageNumber());

        Page<BoardArticleDto> list = boardArticleDtoRepository.findAll(pageable);

        return list.stream().toList();
    }

    public void saveBoard(String title, String content, String username, String localIp) {

        boardArticleDtoRepository.save(BoardArticleDto.builder()
                        .contents(content)
                        .hitCnt(0)
                        .title(title)
                        .localIp(localIp)
                        .userId(username)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                .build());
    }
}

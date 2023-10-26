package com.chosu.jobssimpleboard.board.service;


import com.chosu.jobssimpleboard.board.dto.*;
import com.chosu.jobssimpleboard.board.repository.*;
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
    private RedisBaseRepository redisBaseRepository;
    private UserDtoRepository userDtoRepository;
    private BoardArticleLikeDtoRepository boardArticleLikeDtoRepository;
    public Page<BoardArticleDto> selectList(Pageable pageable){

        log.info("selectList size >> {}", pageable.getPageSize());
        log.info("selectList page >> {}", pageable.getPageNumber());

        Page<BoardArticleDto> list =boardArticleDtoRepository.findAllByOrderByIdDesc(pageable);
        list.forEach(boardArticleDto -> {
            String redisViewCount = redisBaseRepository.getValues("viewCount_" +boardArticleDto.getId());
            if(redisViewCount == null) redisViewCount = "0";
            int viewCount = Integer.parseInt(redisViewCount);
            log.info("redisBaseRepository >> viewCount >>{}" , viewCount);

            if(viewCount > 0){
                boardArticleDto.setViewCount(viewCount);
            }
        });
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

        //boardArticleDtoRepository.updateViewCount(id);

        BoardArticleDto boardArticleDto = boardArticleDtoRepository.findById(id).orElseThrow(NullPointerException::new);


        String redisViewCount = redisBaseRepository.getValues("viewCount_"+ boardArticleDto.getId());
        String insertViewCount = "";
        if(redisViewCount != null){
            insertViewCount = String.valueOf(Integer.parseInt(redisViewCount) +1);
        }else{
            insertViewCount = String.valueOf(boardArticleDto.getViewCount() +1);
        }

        redisBaseRepository.setValues("viewCount_" + boardArticleDto.getId(), insertViewCount);

        String test = redisBaseRepository.getValues("viewCount_" + boardArticleDto.getId());
        log.info("test >>{}", test);

        return boardArticleDto;
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

    @Transactional
    public void delete(Long id) {
        redisBaseRepository.deleteValues("viewCount_" + id);
        boardArticleDtoRepository.deleteById(id);
    }

    public void updateLike(Long boardId, String userId) throws Exception {

        Optional<BoardArticleDto> boardArticleDto = boardArticleDtoRepository.findById(boardId);
        if(boardArticleDto.isEmpty()){
            throw new Exception("게시글 정보가 없습니다.");
        }
        Optional<UserDto> userDto = userDtoRepository.findById(userId);
        if(userDto.isEmpty()){
            throw new Exception("유저 정보가 없습니다.");
        }

        Optional<BoardArticleLikeDto> boardArticleLikeDto
                =  boardArticleLikeDtoRepository.findByUserDtoAndBoardArticleDto(userDto.get(), boardArticleDto.get());
        if(boardArticleLikeDto.isEmpty()){
            boardArticleLikeDtoRepository.save(
                    BoardArticleLikeDto.builder()
                            .likeYn("Y")
                            .userDto(userDto.get())
                            .updateTime(LocalDateTime.now())
                            .createTime(LocalDateTime.now())
                            .build()
            );
        }else{
            boardArticleLikeDtoRepository.save(
                    BoardArticleLikeDto.builder()
                            .id(boardArticleLikeDto.get().getId())
                            .likeYn("N")
                            .boardArticleDto(boardArticleDto.get())
                            .userDto(userDto.get())
                            .updateTime(LocalDateTime.now())
                            .createTime(boardArticleLikeDto.get().getCreateTime())
                            .build()
            );
        }
    }
}

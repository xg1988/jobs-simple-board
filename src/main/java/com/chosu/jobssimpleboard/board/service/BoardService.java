package com.chosu.jobssimpleboard.board.service;


import com.chosu.jobssimpleboard.board.dto.*;
import com.chosu.jobssimpleboard.board.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.Duration;
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

    public static String REDIS_CONFIG_VIEWCOUNT_KEY  = "viewCount_";

    public Page<BoardArticleDto> selectList(Pageable pageable){

        log.info("selectList size >> {}", pageable.getPageSize());
        log.info("selectList page >> {}", pageable.getPageNumber());

        Page<BoardArticleDto> list =boardArticleDtoRepository.findAllByOrderByIdDesc(pageable);
        list.forEach(boardArticleDto -> {
            String redisViewCount = redisBaseRepository.getValues(REDIS_CONFIG_VIEWCOUNT_KEY +boardArticleDto.getId());
            if(redisViewCount == null) redisViewCount = "0";
            int viewCount = Integer.parseInt(redisViewCount);
            log.info("redisBaseRepository >> viewCount >>{}" , viewCount);
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
    }

    @Transactional
    public BoardArticleDto select(Long id) {
        BoardArticleDto boardArticleDto = boardArticleDtoRepository.findById(id).orElseThrow(NullPointerException::new);

        int viewCnt = getViewCnt(boardArticleDto);
        boardArticleDto.setViewCount(viewCnt);
        log.info("viewCnt >>{}", viewCnt);

        int likeYnCnt = boardArticleLikeDtoRepository.countBoardArticleLikeDtoByBoardArticleDtoIdAndLikeYn(id, BoardLikeEnum.LIKE.getLikeYn());
        boardArticleDto.setLikeYnCnt(likeYnCnt);
        log.info("likeYnCnt >>{}", likeYnCnt);

        return boardArticleDto;
    }



    private int getViewCnt(BoardArticleDto boardArticleDto) {
        String redisBoardKey = REDIS_CONFIG_VIEWCOUNT_KEY+ boardArticleDto.getId();

        String redisViewCount = redisBaseRepository.getValues(redisBoardKey);
        String insertViewCount = (redisViewCount != null) ?
                                            String.valueOf(Integer.parseInt(redisViewCount) +1) :
                                            String.valueOf(boardArticleDto.getViewCount() +1);
        // 기한을 1분으로 설정해봄
        redisBaseRepository.setValues(redisBoardKey, insertViewCount, Duration.ofMinutes(1L));

        return Integer.parseInt(redisBaseRepository.getValues(redisBoardKey));
    }

    public BoardArticleLikeDto selectBoardArticleLikeDto(Principal principal, BoardArticleDto boardArticleDto){
        UserDto userDto = userDtoRepository.findById(principal.getName()).orElseThrow(NullPointerException::new);

        BoardArticleLikeDto boardArticleLikeDto = boardArticleLikeDtoRepository.findByUserDtoAndBoardArticleDtoAndLikeYn(userDto, boardArticleDto, "Y").orElse(null);

        log.info("boardArticleLikeDto >> {}" , boardArticleLikeDto);

        return boardArticleLikeDto;
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

    @Transactional
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


        if(boardArticleLikeDto.isEmpty() || BoardLikeEnum.NOTLIKE.getLikeYn().equals(boardArticleLikeDto.get().getLikeYn())){
            boardArticleLikeDtoRepository.save(
                    BoardArticleLikeDto.builder()
                            .likeYn(BoardLikeEnum.LIKE.getLikeYn())
                            .userDto(userDto.get())
                            .boardArticleDto(boardArticleDto.get())
                            .updateTime(LocalDateTime.now())
                            .createTime(LocalDateTime.now())
                            .build()
            );
        }else{
            boardArticleLikeDtoRepository.save(
                    BoardArticleLikeDto.builder()
                            .id(boardArticleLikeDto.get().getId())
                            .likeYn(BoardLikeEnum.NOTLIKE.getLikeYn())
                            .boardArticleDto(boardArticleDto.get())
                            .userDto(userDto.get())
                            .updateTime(LocalDateTime.now())
                            .createTime(boardArticleLikeDto.get().getCreateTime())
                            .build()
            );
        }
    }
}

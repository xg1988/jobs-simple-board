package com.chosu.jobssimpleboard.board.repository;

import com.chosu.jobssimpleboard.board.dto.BoardArticleDto;
import com.chosu.jobssimpleboard.board.dto.BoardArticleLikeDto;
import com.chosu.jobssimpleboard.board.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardArticleLikeDtoRepository extends JpaRepository<BoardArticleLikeDto, Long> {
    public Optional<BoardArticleLikeDto> findByUserDtoAndBoardArticleDto(UserDto userDto, BoardArticleDto boardArticleDto);
    public Optional<BoardArticleLikeDto> findByUserDtoAndBoardArticleDtoAndLikeYn(UserDto userDto, BoardArticleDto boardArticleDto, String likeYn);

    public int countBoardArticleLikeDtoByBoardArticleDtoIdAndLikeYn(Long id, String likeYn);

}

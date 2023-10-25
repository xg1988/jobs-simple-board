package com.chosu.jobssimpleboard.board.repository;

import com.chosu.jobssimpleboard.board.dto.BoardArticleDto;
import com.chosu.jobssimpleboard.board.dto.BoardArticleLikeDto;
import com.chosu.jobssimpleboard.board.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardArticleLikeDtoRepository extends JpaRepository<BoardArticleLikeDto, Long> {
    public List<BoardArticleLikeDto> findByUserDtoAndBoardArticleDto(UserDto userDto, BoardArticleDto boardArticleDto);
}

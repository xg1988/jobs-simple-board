package com.chosu.jobssimpleboard.board.repository;

import com.chosu.jobssimpleboard.board.dto.BoardArticleLikeDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardArticleLikeDtoRepository extends JpaRepository<BoardArticleLikeDto, Long> {
}

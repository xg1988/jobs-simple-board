package com.chosu.jobssimpleboard.board.repository;

import com.chosu.jobssimpleboard.board.dto.BoardArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardArticleDtoRepository extends JpaRepository<BoardArticleDto, Long> {
    public Page<BoardArticleDto> findAll(Pageable pageable);
}

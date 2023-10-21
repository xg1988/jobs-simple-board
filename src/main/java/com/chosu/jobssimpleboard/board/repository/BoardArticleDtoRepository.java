package com.chosu.jobssimpleboard.board.repository;

import com.chosu.jobssimpleboard.board.dto.BoardArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardArticleDtoRepository extends JpaRepository<BoardArticleDto, Long> {
    public Page<BoardArticleDto> findAllByOrderByIdDesc(Pageable pageable);

    @Modifying
    @Query("update BoardArticleDto t set t.viewCount = t.viewCount + 1 where t.id = :id")
    int updateViewCount(Long id);
}

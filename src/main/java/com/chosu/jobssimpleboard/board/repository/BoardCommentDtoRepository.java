package com.chosu.jobssimpleboard.board.repository;

import com.chosu.jobssimpleboard.board.dto.BoardCommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardCommentDtoRepository extends JpaRepository<BoardCommentDto, Long> {

    public List<BoardCommentDto> findByBoardIdOrderByIdDesc(Long boardId);

    public BoardCommentDto findByBoardIdAndIdAndUserId(Long boardId, Long id, String userId);

    public void deleteByBoardIdAndId(Long boardId, Long id);
}

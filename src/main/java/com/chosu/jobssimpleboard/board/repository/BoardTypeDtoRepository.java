package com.chosu.jobssimpleboard.board.repository;

import com.chosu.jobssimpleboard.board.dto.BoardTypeDto;
import com.chosu.jobssimpleboard.board.dto.BoardTypeWriteDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardTypeDtoRepository extends JpaRepository<BoardTypeDto, Long> {
}

package com.chosu.jobssimpleboard.board.repository;

import com.chosu.jobssimpleboard.board.dto.BoardArticleRedisDto;
import com.chosu.jobssimpleboard.board.dto.BoardListRedisDto;
import org.springframework.data.repository.CrudRepository;

public interface BoardListRedisDtoRepository extends CrudRepository<BoardListRedisDto, Long> {
}

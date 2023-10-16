package com.chosu.jobssimpleboard.board.repository;

import com.chosu.jobssimpleboard.board.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDtoRepository extends JpaRepository<UserDto, String> {
}

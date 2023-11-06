package com.chosu.jobssimpleboard.board.repository;

import com.chosu.jobssimpleboard.board.dto.OAuthUserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthUserDtoRepository extends JpaRepository<OAuthUserDto, Long> {

    Optional<OAuthUserDto> findByEmail(String email);
}

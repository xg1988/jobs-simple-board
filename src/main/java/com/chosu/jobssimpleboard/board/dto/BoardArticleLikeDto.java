package com.chosu.jobssimpleboard.board.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "sample_board_like")
public class BoardArticleLikeDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserDto userDto;

    @OneToOne
    @JoinColumn(name="id")
    private BoardArticleDto boardArticleDto;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}

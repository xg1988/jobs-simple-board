package com.chosu.jobssimpleboard.board.dto;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "simple_board_like")
public class BoardArticleLikeDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserDto userDto;

    @ManyToOne
    @JoinColumn(referencedColumnName="id")
    private BoardArticleDto boardArticleDto;

    private String likeYn;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

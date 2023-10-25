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
@Table(name = "simple_board_like")
public class BoardArticleLikeDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserDto userDto;

    @ManyToOne
    @JoinColumn(name="id", insertable = false, updatable = false)
    private BoardArticleDto boardArticleDto;

    private String likeYn;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

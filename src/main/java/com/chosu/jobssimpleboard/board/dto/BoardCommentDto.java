package com.chosu.jobssimpleboard.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name="simple_board_comment")
public class BoardCommentDto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;
    private Long boardTypeId;

    private String comment;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String localIp;

    @Column(nullable = false)
    private LocalDateTime createTime;
    @Column(nullable = false)
    private LocalDateTime updateTime;
}

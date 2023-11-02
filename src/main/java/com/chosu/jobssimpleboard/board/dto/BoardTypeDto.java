package com.chosu.jobssimpleboard.board.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="simple_board_type")
public class BoardTypeDto {
    @Id
    @Column(name = "board_type_id", nullable = false)
    private String id;
    @Column(nullable = false)
    private String boardTypeDesc;
    @Column(nullable = false)
    private LocalDateTime createTime;
    @Column(nullable = false)
    private LocalDateTime updateTime;
}

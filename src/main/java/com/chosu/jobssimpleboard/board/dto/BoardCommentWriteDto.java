package com.chosu.jobssimpleboard.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardCommentWriteDto {

    private String comment;
    private Long boardId;
}

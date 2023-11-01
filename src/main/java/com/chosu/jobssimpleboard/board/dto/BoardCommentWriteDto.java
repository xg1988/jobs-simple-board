package com.chosu.jobssimpleboard.board.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class BoardCommentWriteDto {

    private String comment;
    private Long boardId;
    private String localIp;
}

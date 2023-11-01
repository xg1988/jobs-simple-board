package com.chosu.jobssimpleboard.board.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BoardCommentWriteDto {

    private String id;
    private String comment;
    private String boardId;
    private String localIp;

    public void updateLocalIp(String localIp){
        this.localIp = localIp;
    }
}

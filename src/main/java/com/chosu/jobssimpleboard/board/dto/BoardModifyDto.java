package com.chosu.jobssimpleboard.board.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardModifyDto {

    private Long id;
    private String title;
    private String contents;
}

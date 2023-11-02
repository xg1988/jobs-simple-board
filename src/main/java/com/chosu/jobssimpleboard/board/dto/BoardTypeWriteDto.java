package com.chosu.jobssimpleboard.board.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardTypeWriteDto {
    private String boardTypeId;
    private String boardTypeDesc;
}

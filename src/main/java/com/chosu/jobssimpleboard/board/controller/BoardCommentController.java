package com.chosu.jobssimpleboard.board.controller;

import com.chosu.jobssimpleboard.board.dto.BoardCommentDto;
import com.chosu.jobssimpleboard.board.dto.BoardCommentWriteDto;
import com.chosu.jobssimpleboard.board.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardCommentController {

    private final BoardCommentService boardCommentService;

    @PostMapping(value = "/comment")
    public String insert(BoardCommentWriteDto boardCommentWriteDto, Principal principal){
        BoardCommentDto boardCommentDto = boardCommentService.insertComment(
                boardCommentWriteDto
                , principal
        );

        return "redirect:/detail/" + boardCommentDto.getBoardId();
    }
}

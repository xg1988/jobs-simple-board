package com.chosu.jobssimpleboard.board.controller;

import com.chosu.jobssimpleboard.board.dto.BoardCommentDto;
import com.chosu.jobssimpleboard.board.dto.BoardCommentWriteDto;
import com.chosu.jobssimpleboard.board.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardCommentController {

    private final BoardCommentService boardCommentService;

    @PostMapping(value = "/comment")
    public String insert(BoardCommentWriteDto boardCommentWriteDto, Principal principal, HttpServletRequest request){

        boardCommentWriteDto.updateLocalIp(request.getRemoteAddr());
        BoardCommentDto boardCommentDto = boardCommentService.insertComment(
                boardCommentWriteDto
                , principal
        );

        return "redirect:/detail/" + boardCommentDto.getBoardId();
    }

    @DeleteMapping(value="/comment")
    public String delete(BoardCommentWriteDto boardCommentWriteDto, Principal principal){
        boardCommentService.deleteComment(
                boardCommentWriteDto
                , principal
        );
        return "redirect:/detail/" + boardCommentWriteDto.getBoardId();
    }
}

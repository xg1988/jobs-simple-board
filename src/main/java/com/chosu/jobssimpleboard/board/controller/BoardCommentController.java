package com.chosu.jobssimpleboard.board.controller;

import com.chosu.jobssimpleboard.board.dto.BoardCommentDto;
import com.chosu.jobssimpleboard.board.dto.BoardCommentWriteDto;
import com.chosu.jobssimpleboard.board.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        log.info("boardCommentWriteDto >> {}" , boardCommentWriteDto);

        BoardCommentDto boardCommentDto = boardCommentService.insertComment(
                BoardCommentWriteDto.builder()
                        .comment(boardCommentWriteDto.getComment())
                        .boardId(boardCommentWriteDto.getBoardId())
                        .localIp(request.getRemoteAddr())
                        .build()
                , principal
        );

        return "redirect:/detail/" + boardCommentDto.getBoardId();
    }

    @GetMapping(value="/comment/{boardId}/{id}")
    public String delete(@PathVariable Long boardId, @PathVariable Long id, Principal principal){
        boardCommentService.deleteComment(boardId, id);
        return "redirect:/detail/" + boardId;
    }
}

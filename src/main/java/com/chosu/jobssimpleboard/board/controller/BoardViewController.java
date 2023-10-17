package com.chosu.jobssimpleboard.board.controller;


import com.chosu.jobssimpleboard.board.dto.BoardArticleDto;
import com.chosu.jobssimpleboard.board.dto.BoardWriteDto;
import com.chosu.jobssimpleboard.board.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class BoardViewController {

    private BoardService boardService;

    @GetMapping(value = "/")
    public String index(){
        return "index";
    }


    @GetMapping(value = "/board")
    public String board(Model model, Pageable pageable){
        List<BoardArticleDto> list = boardService.selectList(pageable);
        model.addAttribute("list", list);
        return "board";
    }

    @PostMapping(value = "/board")
    public String saveBoard(HttpServletRequest httpServletRequest, Principal principal, BoardWriteDto boardWriteDto){


        String title = boardWriteDto.getTitle();
        String content = boardWriteDto.getContents();
        String userId = principal.getName();
        String localIp = httpServletRequest.getLocalAddr();

        log.info("saveBoard boardWriteDto >>{}", boardWriteDto);
        log.info("saveBoard title >>{}", title);
        log.info("saveBoard content >>{}", content);
        log.info("saveBoard  userId>> {}" , userId);
        log.info("saveBoard  localIp>> {}" , localIp);


        boardService.saveBoard(title, content, userId, localIp);
        return "redirect:/board";
    }

    @GetMapping(value = "/write")
    public String write(){
        return "write";
    }
}

package com.chosu.jobssimpleboard.board.controller;


import com.chosu.jobssimpleboard.board.dto.*;
import com.chosu.jobssimpleboard.board.service.BoardCommentService;
import com.chosu.jobssimpleboard.board.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardViewController {

    private final BoardService boardService;
    private final BoardCommentService boardCommentService;

    @GetMapping(value = "/")
    public String index(){
        return "index";
    }

    @GetMapping(value = "/board")
    public String board(Model model, @PageableDefault(size = 10) Pageable pageable){

        Page<BoardArticleDto> list = boardService.selectList(pageable);

        int totalPages = list.getTotalPages();
        int totalElements = (int) list.getTotalElements();
        int pageNumber = list.getPageable().getPageNumber();
        int pageSize = list.getPageable().getPageSize();

        model.addAttribute("list", list);
        model.addAttribute("totalPages", totalPages-1);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        return "board";
    }

    @GetMapping(value = "/detail/{id}")
    public String boardDetail(Model model, Principal principal, @PathVariable Long id){
        log.info("id >> {}" , id);
        log.info("principal.getName() >> {}" , principal.getName());

        BoardArticleDto boardArticleDto = boardService.select(id);

        BoardArticleLikeDto boardArticleLikeDto = boardService.selectBoardArticleLikeDto(principal, boardArticleDto);

        List<BoardCommentDto> boardCommentDtos = boardCommentService.getComments(id);

        model.addAttribute("boardArticleDto" , boardArticleDto);
        model.addAttribute("boardArticleLikeDto" , boardArticleLikeDto);
        model.addAttribute("sessionUserId", principal.getName());
        model.addAttribute("boardCommentDtos", boardCommentDtos);

        return "detail";
    }

    @PostMapping(value = "/board")
    public String saveBoard(HttpServletRequest httpServletRequest, Principal principal, BoardWriteDto boardWriteDto){


        String title = boardWriteDto.getTitle();
        String content = boardWriteDto.getContents();
        String userId = principal.getName();
        String localIp = httpServletRequest.getRemoteAddr();

        log.info("saveBoard boardWriteDto >>{}", boardWriteDto);
        log.info("saveBoard title >>{}", title);
        log.info("saveBoard content >>{}", content);
        log.info("saveBoard  userId>> {}" , userId);
        log.info("saveBoard  localIp>> {}" , localIp);


        boardService.saveBoard(title, content, userId, localIp);
        return "redirect:/board";
    }

    @GetMapping(value ="/write")
    public String write(){
        return "write";
    }
    @GetMapping(value ="/modify/{id}")
    public String modify(Model model, @PathVariable Long id){
        if(id != null){
            BoardArticleDto boardArticleDto = boardService.select(id);
            model.addAttribute("boardArticleDto", boardArticleDto);
        }
        return "modify";
    }

    @PostMapping(value ="/modify")
    public String modifyBoard(Model model, BoardModifyDto boardModifyDto){
        log.info("modifyBoard post called");
        boardService.modifyBoard(boardModifyDto);
        return "redirect:/board";
    }

    @GetMapping(value ="/delete/{id}")
    public String delete(Model model, @PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board";
    }

    @GetMapping(value = "/updateLike/{boardId}")
    public String updateLike(@PathVariable String boardId, Principal principal) throws Exception {
        log.info("principal.getName() >>{}", principal.getName());

        boardService.updateLike(Long.parseLong(boardId), principal.getName());

        return "redirect:/detail/" + Long.parseLong(boardId);
    }
}

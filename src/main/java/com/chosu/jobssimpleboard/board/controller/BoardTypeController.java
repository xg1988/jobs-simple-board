package com.chosu.jobssimpleboard.board.controller;


import com.chosu.jobssimpleboard.board.dto.BoardTypeDto;
import com.chosu.jobssimpleboard.board.dto.BoardTypeWriteDto;
import com.chosu.jobssimpleboard.board.service.BoardTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardTypeController {

    private final BoardTypeService boardTypeService;

    @GetMapping(value = "/type")
    public String type(Model model){

        List<BoardTypeDto> boardTypeDtos = boardTypeService.selectList();

        model.addAttribute("boardTypeDtos", boardTypeDtos);
        return "type";
    }

    @PostMapping(value = "/type")
    public String insertType(BoardTypeWriteDto boardTypeWriteDto){
        log.info("boardTypeWriteDto>> {}", boardTypeWriteDto);

        boardTypeService.insertType(boardTypeWriteDto);

        return "redirect:/type";
    }

    @GetMapping(value = "/deleteType/{typeId}")
    public String deleteType(@PathVariable String typeId){
        boardTypeService.deleteType(typeId);

        return "redirect:/type";
    }
}

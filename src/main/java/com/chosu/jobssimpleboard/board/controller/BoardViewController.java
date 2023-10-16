package com.chosu.jobssimpleboard.board.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class BoardViewController {

    @GetMapping(value = "/")
    public String index(){
        return "index";
    }
    @GetMapping(value = "/board")
    public String board(){
        return "board";
    }
}

package com.chosu.jobssimpleboard.board.controller;


import com.chosu.jobssimpleboard.board.dto.UserDto;
import com.chosu.jobssimpleboard.board.dto.UserSecurityDto;
import com.chosu.jobssimpleboard.board.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@Slf4j
public class LoginController {
    private LoginService loginService;

    @GetMapping(value = "/signup/{userId}/{password}/{email}/{name}/{role}")
    public UserSecurityDto signup(
            @PathVariable String userId
            , @PathVariable String password
            , @PathVariable String email
            , @PathVariable String name
            , @PathVariable String role) throws Exception {
        return new UserSecurityDto(loginService.signup(
                userId, password, email, name, role
        ));
    }

    @PostMapping(value = "/signup")
    public String signup (UserDto userDto, RedirectAttributes redirectAttributes) throws Exception {
        log.info("signup userDto>> ,{}" , userDto);
        UserSecurityDto userSecurityDto =  loginService.signup(userDto);
        log.info("signup userSecurityDto>> ,{}" , userSecurityDto);
        //redirectAttributes.addAttribute
        //redirect 시에 객체 전달하는 방법
        redirectAttributes.addFlashAttribute("userDto", userSecurityDto.getUserDto());
        return "redirect:/";
    }
}

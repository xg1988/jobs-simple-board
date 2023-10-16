package com.chosu.jobssimpleboard.board.controller;


import com.chosu.jobssimpleboard.board.dto.UserSecurityDto;
import com.chosu.jobssimpleboard.board.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
}

package com.chosu.jobssimpleboard.common.config;


import com.chosu.jobssimpleboard.board.dto.Role;
import com.chosu.jobssimpleboard.board.service.OAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(1)
public class OAuthSecurityConfig {
    private final OAuthUserService oAuthUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/profile").permitAll()
                .antMatchers("/api/**").hasRole(Role.USER.name())
                .anyRequest().authenticated().and()
                .logout().logoutSuccessUrl("/").and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(oAuthUserService);
        return http.build();
    }
}


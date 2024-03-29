package com.chosu.jobssimpleboard.common.config;


import com.chosu.jobssimpleboard.board.service.LoginService;
import com.chosu.jobssimpleboard.board.service.OAuthLoginService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final LoginService loginService;
    private final OAuthLoginService oAuthLoginService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().
                requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .requestMatchers(new AntPathRequestMatcher( "/favicon.ico"))
                .requestMatchers(new AntPathRequestMatcher( "/css/**"))
                .requestMatchers(new AntPathRequestMatcher( "/js/**"))
                .requestMatchers(new AntPathRequestMatcher( "/img/**"))
                .requestMatchers(new AntPathRequestMatcher( "/lib/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.info("WebSecurityConfig filterChain");

        // csrf 비활성화 (사이트 요청 위조) ---> csrf 토큰이 없어도 서버는 응답
        // 스프링에서는 csrf 기본은 활성화 (보안 목적) ---> csrf 토큰을 url에 포함해야 서버는 응답

        http
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/", "/signup", "/login-form", "/oauth2/**").permitAll() // permitAll을 한다고 해서 전체 필터가 제외되는 것은 아님, 전체 필터 제외는 web ignoring
                .antMatchers("/user/**").hasAuthority("USER") // hasAuthority
                .antMatchers("/admin/**").hasAuthority("ADMIN") // hasAuthority
                .antMatchers("/board/**").hasAuthority("USER") // hasAuthority
                //.antMatchers("/user/**").hasRole("USER") // hasRole에서는 DB저장 시 ROLE_ prefix 붙여서 저장
                .anyRequest().authenticated();
/*
        http
                .authenticationManager(authenticationManager())
                .exceptionHandling().accessDeniedPage("/access-denied");
*/
        http
                .formLogin().disable();

        http.oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorize")
                .and()
                .redirectionEndpoint().baseUri("/oauth2/callback/**")
                .and()
                .userInfoEndpoint().userService(oAuthLoginService);



        /*http.oauth2Login()
                .userInfoEndpoint().userService(oAuthLoginService);*/
/*        http.oauth2Login()
                .loginPage("/oauth/login")
                .loginProcessingUrl("/oauth/naver")
                .userInfoEndpoint().userService(oAuthLoginService);*/

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }
/*
    @Bean
    public BCryptPasswordEncoder getbCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
}

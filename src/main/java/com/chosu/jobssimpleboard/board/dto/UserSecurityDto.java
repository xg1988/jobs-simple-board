package com.chosu.jobssimpleboard.board.dto;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@ToString
@Slf4j
public class UserSecurityDto implements UserDetails {

    private final UserDto userDto;
    public UserSecurityDto(UserDto userDto){
        this.userDto = userDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        //GrantedAuthority를 Collection안에 담기
        collect.add((GrantedAuthority) () -> {
            log.info("userDto.getRole() >> {}", userDto.getRole());
            return userDto.getRole(); //여기서 역할 뽑기
        });
        return collect;


    }

    @Override
    public String getPassword() {
        return userDto.getPassword();
    }

    @Override
    public String getUsername() {
        return userDto.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

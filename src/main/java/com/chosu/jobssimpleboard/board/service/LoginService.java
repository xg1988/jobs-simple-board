package com.chosu.jobssimpleboard.board.service;

import com.chosu.jobssimpleboard.board.dto.UserDto;
import com.chosu.jobssimpleboard.board.dto.UserSecurityDto;
import com.chosu.jobssimpleboard.board.repository.UserDtoRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements UserDetailsService {
    private final UserDtoRepository userDtoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto signup(
            String userId
            , String password
            , String email
            , String name
            , String role
    ) throws Exception {
        /**
         * 회원 가입 조건 체크
         */

        /**
         * 중복 회원가입 체크
         */

        Optional<UserDto> userDto = userDtoRepository.findById(userId);
        if(userDto.isPresent()){
            throw new Exception("이미 존재하는 아이디 입니다.");
        }

        return userDtoRepository.save(
                UserDto.builder()
                        .email(email)
                        .username(name)
                        .password(bCryptPasswordEncoder.encode(password)) // bCryptPasswordEncoder 를 사용한 암호화
                        .userId(userId)
                        //.role("ROLE_"+role)
                        .role(role) // hasAuthority를 사용하게 때문에 prefix 제외
                        .build());
    }

    public UserSecurityDto signup(UserDto userDto) throws Exception {
        /**
         * 회원 가입 조건 체크
         */

        /**
         * 중복 회원가입 체크
         */

        Optional<UserDto> checkUserDto = userDtoRepository.findById(userDto.getUserId());
        if(checkUserDto.isPresent()){
            throw new Exception("이미 존재하는 아이디 입니다.");
        }
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        return new UserSecurityDto(userDtoRepository.save(userDto));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username: {}" , username);

        UserDto userDto = null;
        try{
            userDto = userDtoRepository.findById(username).orElseThrow(NullPointerException::new);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        UserSecurityDto userSecurityDto = new UserSecurityDto(userDto);
        log.info("userSecurityDto: {}" , userSecurityDto);

        return userSecurityDto;
    }
}

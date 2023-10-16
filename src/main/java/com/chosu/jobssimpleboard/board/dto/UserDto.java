package com.chosu.jobssimpleboard.board.dto;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@Entity(name="user_info")
public class UserDto {

    @Id
    String userId;
    String email;
    String password;
    String name;
    String role;
}

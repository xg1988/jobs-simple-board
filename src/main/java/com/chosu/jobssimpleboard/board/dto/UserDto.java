package com.chosu.jobssimpleboard.board.dto;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter // form 전송을 위해서는 setter가 있어야하지만, 안전한 객체 관리를 위해서 setter는 막는게 좋다.
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

package com.chosu.jobssimpleboard.board.dto;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter // form 전송을 위해서는 setter가 있어야하지만, 안전한 객체 관리를 위해서 setter는 막는게 좋다.
@Builder
@ToString
@Entity
@Table(name="user_info")
public class UserDto {

    @Id
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String role;
}

package com.chosu.jobssimpleboard.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@Getter
@Entity(name = "oauth_user_info")
public class OAuthUserDto {

    public OAuthUserDto() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(name = "profile_picture")
    private String picture;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String age; // 연령대

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public OAuthUserDto update(String name, String picture) {
        this.nickname = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}

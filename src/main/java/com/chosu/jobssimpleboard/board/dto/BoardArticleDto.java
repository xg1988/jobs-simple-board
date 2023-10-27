package com.chosu.jobssimpleboard.board.dto;


import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@ToString
@Table(name="simple_board_info")
public class BoardArticleDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 입력을 생략하면 DBMS가 알아서 해줌
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;
    @Column(nullable = false, name = "userId")
    private String userId;
    @Column(nullable = false)
    private String localIp;
    @Column(nullable = false)
    private int viewCount;
    @Column(nullable = false)
    private LocalDateTime createTime;
    @Column(nullable = false)
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "boardArticleDto", cascade = CascadeType.MERGE)
    private List<BoardArticleLikeDto> list = new ArrayList<>();
}

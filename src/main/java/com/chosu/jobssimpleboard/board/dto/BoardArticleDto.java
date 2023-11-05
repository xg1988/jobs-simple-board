package com.chosu.jobssimpleboard.board.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name="simple_board_info")
public class BoardArticleDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 입력을 생략하면 DBMS가 알아서 해줌
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;
    /*@Column(nullable = false, name = "userId")
    private String userId;*/

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserDto userDto;

    @Column(nullable = false)
    private String localIp;
    @Column(nullable = false)
    private int viewCount;
    @Column(nullable = false)
    private LocalDateTime createTime;
    @Column(nullable = false)
    private LocalDateTime updateTime;

    // DB 미포함
    @Transient
    private int likeYnCount;

    public void updateViewCount(int viewCount){
        this.viewCount = viewCount;
    }

    public void updateLikeYnCount(int likeYnCount){
        this.likeYnCount = likeYnCount;
    }


    @OneToOne
    @JoinColumn(name = "board_type_id")
    private BoardTypeDto boardTypeDto;

/*
    @OneToMany(mappedBy = "boardArticleDto", cascade = CascadeType.MERGE)
    private List<BoardArticleLikeDto> list = new ArrayList<>();

 */
}

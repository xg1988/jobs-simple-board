package com.chosu.jobssimpleboard.board.dto;

public enum BoardLikeEnum {


    LIKE("Y"), NOTLIKE("N");

    private String likeYn;

    BoardLikeEnum(String likeYn) {
        this.likeYn = likeYn;
    }

    public String getLikeYn(){
        return this.likeYn;
    }
}

package com.multi.fourtunes.model.dto;

import lombok.Data;

@Data
public class CommentDto {
    private int boardNo;
    private int userNo;
    private String comment;
    private Integer reportCnt;
}

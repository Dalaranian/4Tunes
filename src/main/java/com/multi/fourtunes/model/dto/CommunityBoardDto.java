package com.multi.fourtunes.model.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CommunityBoardDto {
    private int boardNo;
    private String title;
    private String content;
    private int userNo;
    private Integer reportCnt;
    private Date writeDate;
    private Integer viewCnt;
}

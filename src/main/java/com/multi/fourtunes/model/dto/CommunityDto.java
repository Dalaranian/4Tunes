package com.multi.fourtunes.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class CommunityDto {
    private int boardNo;
    private String title;
    private String content;
    private int userNo;
    private int reportCount;
    private Date writeDate;
    private int viewCount;
}

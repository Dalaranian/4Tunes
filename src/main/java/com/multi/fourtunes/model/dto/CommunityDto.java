package com.multi.fourtunes.model.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CommunityDto {
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private int userNo;
	private int boardReportCnt;
	private Date boardWriteDate;
	private int boardViewCnt;
}

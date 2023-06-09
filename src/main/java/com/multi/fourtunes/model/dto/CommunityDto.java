package com.multi.fourtunes.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommunityDto {
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private int userNo;
	private int boardReportCnt;
	private Date boardWriteDate;
	private int boardViewCnt;
}
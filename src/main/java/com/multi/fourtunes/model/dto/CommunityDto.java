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
	private int BOARD_NO;
	private String BOARD_TITLE	;
	private String BOARD_CONTENT;
	private int USER_NO;
	private int BOARD_REPORT_CNT;
	private Date BOARD_WRITE_DATE;
	private int BOARD_VIEW_CNT;
}
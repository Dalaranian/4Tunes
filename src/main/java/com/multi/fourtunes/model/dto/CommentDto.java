package com.multi.fourtunes.model.dto;

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
public class CommentDto {
	private int commentNo;
	private int boardNo;
	private int userNo;
	private String commentContent;
	private int commentReportCnt;
	private String user_name;
	private String boardTitle;
}

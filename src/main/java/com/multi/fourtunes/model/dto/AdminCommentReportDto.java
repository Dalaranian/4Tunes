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
public class AdminCommentReportDto {
	private int comment_no;
	private int board_no;
	private int user_no;
	private String comment_content;
	private int comment_report_cnt;
	private String user_name;
	private String user_id;
	private String board_title;
}

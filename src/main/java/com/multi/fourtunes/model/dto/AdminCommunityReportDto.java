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
public class AdminCommunityReportDto {
	private int board_no;
	private String board_title;
	private String board_content;
	private int user_no;
	private int board_report_cnt;
	private Date board_write_date;
	private int board_view_cnt;
	private String user_name;
	private String user_id;
}

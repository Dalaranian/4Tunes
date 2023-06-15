package com.multi.fourtunes.model.dao;

import java.util.List;

import com.multi.fourtunes.model.dto.AdminCommentReportDto;
import com.multi.fourtunes.model.dto.AdminCommunityReportDto;

public interface ReportDao {

	public List<AdminCommunityReportDto> selectReport();

	public int confirmReport(int board_no);

	public int deleteReport(int board_no);

	public List<AdminCommentReportDto> selectReportComment();

	public int confirmReportComment(int comment_no);

	public int deleteReportComment(int comment_no);

}

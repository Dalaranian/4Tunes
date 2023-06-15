package com.multi.fourtunes.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.dto.AdminCommentReportDto;
import com.multi.fourtunes.model.dto.AdminCommunityReportDto;
import com.multi.fourtunes.model.mapper.ReportMapper;

@Repository
public class ReportDaoImpl implements ReportDao{

	@Autowired
	ReportMapper reportMapper;
	
	@Override
	public List<AdminCommunityReportDto> selectReport() {
		return reportMapper.selectReport();
	}

	@Override
	public int confirmReport(int board_no) {
		return reportMapper.confirmReport(board_no);
	}

	@Override
	public int deleteReport(int board_no) {
		return reportMapper.deleteReport(board_no);
	}

	@Override
	public List<AdminCommentReportDto> selectReportComment() {
		return reportMapper.selectReportComment();
	}

	@Override
	public int confirmReportComment(int comment_no) {
		return reportMapper.confirmReportComment(comment_no);
	}

	@Override
	public int deleteReportComment(int comment_no) {
		return reportMapper.deleteReportComment(comment_no);
	}

}

package com.multi.fourtunes.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.multi.fourtunes.model.dto.AdminCommentReportDto;
import com.multi.fourtunes.model.dto.AdminCommunityReportDto;

@Mapper
public interface ReportMapper {

	@Select(" SELECT b.*, u.USER_NAME, u.USER_ID "
			+ "FROM COMMUNITY_BOARD b "
			+ "JOIN USER u ON b.USER_NO = u.USER_NO "
			+ "WHERE b.BOARD_REPORT_CNT >= 5 ")
	List<AdminCommunityReportDto> selectReport();
	
	@Update(" UPDATE COMMUNITY_BOARD SET BOARD_REPORT_CNT=0 WHERE BOARD_NO=#{board_no} ")
	int confirmReport(int board_no);

	@Delete(" DELETE FROM COMMUNITY_REPORT WHERE BOARD_NO=#{board_no} ")
	int deleteReport(int board_no);

	@Select(" SELECT c.*, u.USER_NAME, u.USER_ID, b.BOARD_TITLE "
			+ "FROM COMMENT c "
			+ "JOIN USER u ON c.USER_NO = u.USER_NO "
			+ "JOIN COMMUNITY_BOARD b ON c.BOARD_NO = b.BOARD_NO "
			+ "WHERE c.COMMENT_REPORT_CNT >= 5 ")
	List<AdminCommentReportDto> selectReportComment();

	@Update(" UPDATE COMMENT SET COMMENT_REPORT_CNT=0 WHERE COMMENT_NO=#{comment_no} ")
	int confirmReportComment(int comment_no);

	@Delete(" DELETE FROM COMMENT_REPORT WHERE COMMENT_NO=#{comment_no} ")
	int deleteReportComment(int comment_no);

}

package com.multi.fourtunes.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.multi.fourtunes.model.dto.CommentDto;
import com.multi.fourtunes.model.dto.CommentReportDto;
import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.dto.CommunityReportDto;

@Mapper
public interface CommunityMapper {

	@Results({@Result(property = "boardNo", column = "BOARD_NO"),
			@Result(property = "boardTitle", column = "BOARD_TITLE"),
			@Result(property = "boardContent", column = "BOARD_CONTENT"),
			@Result(property = "userNo", column = "USER_NO"),
			@Result(property = "boardReportCnt", column = "BOARD_REPORT_CNT"),
			@Result(property = "boardWriteDate", column = "BOARD_WRITE_DATE"),
			@Result(property = "boardViewCnt", column = "BOARD_VIEW_CNT")})
	@Result(property = "user_name", column = "USER_NAME")
	@Select("SELECT b.*, u.USER_NAME " + "FROM COMMUNITY_BOARD b " + "JOIN USER u ON b.USER_NO = u.USER_NO "
			+ "ORDER BY BOARD_WRITE_DATE DESC")
	List<CommunityDto> getAll();

	@Results({@Result(property = "boardNo", column = "BOARD_NO"),
			@Result(property = "boardTitle", column = "BOARD_TITLE"),
			@Result(property = "boardContent", column = "BOARD_CONTENT"),
			@Result(property = "userNo", column = "USER_NO"),
			@Result(property = "boardReportCnt", column = "BOARD_REPORT_CNT"),
			@Result(property = "boardWriteDate", column = "BOARD_WRITE_DATE"),
			@Result(property = "boardViewCnt", column = "BOARD_VIEW_CNT")})
	@Select("SELECT b.BOARD_NO, b.BOARD_TITLE, b.BOARD_CONTENT, b.USER_NO, b.BOARD_REPORT_CNT, b.BOARD_WRITE_DATE, b.BOARD_VIEW_CNT, u.USER_NAME "
			+ "FROM COMMUNITY_BOARD b " + "JOIN USER u ON b.USER_NO = u.USER_NO " + "WHERE b.BOARD_NO = #{boardNo}")
	CommunityDto get(int boardNo);

	// 조회수
	@Update("UPDATE COMMUNITY_BOARD SET BOARD_VIEW_CNT = BOARD_VIEW_CNT + 1 WHERE BOARD_NO = #{boardNo}")
	int incrementViewCount(int boardNo);

	@Results({@Result(property = "boardNo", column = "BOARD_NO"),
			@Result(property = "boardTitle", column = "BOARD_TITLE"),
			@Result(property = "boardContent", column = "BOARD_CONTENT"),
			@Result(property = "userNo", column = "USER_NO"),
			@Result(property = "boardReportCnt", column = "BOARD_REPORT_CNT"),
			@Result(property = "boardWriteDate", column = "BOARD_WRITE_DATE"),
			@Result(property = "boardViewCnt", column = "BOARD_VIEW_CNT")})
	@Insert("INSERT INTO COMMUNITY_BOARD (BOARD_TITLE, BOARD_CONTENT, USER_NO, BOARD_WRITE_DATE, BOARD_VIEW_CNT) "
			+ "VALUES (#{boardTitle}, #{boardContent}, #{userNo}, NOW(), #{boardViewCnt})")
	int insert(CommunityDto community);


	@Results({@Result(property = "boardNo", column = "BOARD_NO"),
			@Result(property = "boardTitle", column = "BOARD_TITLE"),
			@Result(property = "boardContent", column = "BOARD_CONTENT"),
			@Result(property = "userNo", column = "USER_NO"),
			@Result(property = "boardReportCnt", column = "BOARD_REPORT_CNT"),
			@Result(property = "boardWriteDate", column = "BOARD_WRITE_DATE"),
			@Result(property = "boardViewCnt", column = "BOARD_VIEW_CNT")})
	@Update("UPDATE COMMUNITY_BOARD SET BOARD_TITLE = #{boardTitle}, BOARD_CONTENT = #{boardContent} WHERE BOARD_NO = #{boardNo}")
	int update(CommunityDto community);

	@Results({@Result(property = "boardNo", column = "BOARD_NO"),
			@Result(property = "boardTitle", column = "BOARD_TITLE"),
			@Result(property = "boardContent", column = "BOARD_CONTENT"),
			@Result(property = "userNo", column = "USER_NO"),
			@Result(property = "boardReportCnt", column = "BOARD_REPORT_CNT"),
			@Result(property = "boardWriteDate", column = "BOARD_WRITE_DATE"),
			@Result(property = "boardViewCnt", column = "BOARD_VIEW_CNT")})
	@Delete("DELETE FROM COMMUNITY_BOARD WHERE BOARD_NO = #{boardNo}")
	int delete(int boardNo);

	@Results({@Result(property = "commentNo", column = "COMMENT_NO"),
			@Result(property = "boardNo", column = "BOARD_NO"), @Result(property = "userNo", column = "USER_NO"),
			@Result(property = "commentContent", column = "COMMENT_CONTENT"),
			@Result(property = "commentReportCnt", column = "COMMENT_REPORT_CNT"),
			@Result(property = "user_name", column = "USER_NAME")})
	@Select("SELECT c.COMMENT_NO, c.BOARD_NO, c.USER_NO, c.COMMENT_CONTENT, c.COMMENT_REPORT_CNT, u.USER_NAME "
			+ "FROM COMMENT c " + "JOIN USER u ON c.USER_NO = u.USER_NO " + "WHERE c.BOARD_NO = #{boardNo}")
	List<CommentDto> getComments(int boardNo);

	@Results({@Result(property = "commentNo", column = "COMMENT_NO"),
			@Result(property = "boardNo", column = "BOARD_NO"), @Result(property = "userNo", column = "USER_NO"),
			@Result(property = "commentContent", column = "COMMENT_CONTENT"),
			@Result(property = "commentReportCnt", column = "COMMENT_REPORT_CNT"),
			@Result(property = "user_name", column = "USER_NAME")})
	@Insert("INSERT INTO COMMENT (COMMENT_NO, BOARD_NO, USER_NO, COMMENT_CONTENT, COMMENT_REPORT_CNT) "
			+ "VALUES (#{commentNo}, #{boardNo}, #{userNo}, #{commentContent}, #{commentReportCnt})")
	int addComment(CommentDto comment);

	@Results({@Result(property = "boardNo", column = "BOARD_NO"), @Result(property = "userNo", column = "USER_NO"),
			@Result(property = "commentContent", column = "COMMENT_CONTENT"),
			@Result(property = "commentReportCnt", column = "COMMENT_REPORT_CNT"),
			@Result(property = "user_name", column = "USER_NAME")})
	@Delete("DELETE FROM COMMENT WHERE BOARD_NO = #{boardNo}")
	int deleteByBoardNo(int boardNo);

	@Results({@Result(property = "commentNo", column = "COMMENT_NO"),
			@Result(property = "boardNo", column = "BOARD_NO"), @Result(property = "userNo", column = "USER_NO"),
			@Result(property = "commentContent", column = "COMMENT_CONTENT"),
			@Result(property = "commentReportCnt", column = "COMMENT_REPORT_CNT")})
	@Select("SELECT * FROM COMMENT WHERE COMMENT_NO = #{commentNo}")
	CommentDto getComment(int commentNo);

	@Results({@Result(property = "commentNo", column = "COMMENT_NO"),
			@Result(property = "boardNo", column = "BOARD_NO"), @Result(property = "userNo", column = "USER_NO"),
			@Result(property = "commentContent", column = "COMMENT_CONTENT"),
			@Result(property = "commentReportCnt", column = "COMMENT_REPORT_CNT")})
	@Delete("DELETE FROM COMMENT WHERE COMMENT_NO = #{commentNo}")
	int deleteComment(int commentNo);

	@Select("SELECT COUNT(*) FROM COMMUNITY_REPORT WHERE USER_NO = #{user_no} AND BOARD_NO = #{boardNo}")
	int isReported(int user_no, int boardNo);

	@Update("UPDATE COMMUNITY_BOARD SET BOARD_REPORT_CNT = BOARD_REPORT_CNT + 1 WHERE BOARD_NO = #{boardNo}")
	void incrementReportCount(int boardNo);

	@Insert("INSERT INTO COMMUNITY_REPORT (USER_NO, BOARD_NO) VALUES (#{userNo}, #{boardNo})")
	void reportCommunity(CommunityReportDto reportDto);

	@Select("SELECT COUNT(*) FROM COMMENT_REPORT WHERE USER_NO = #{user_no} AND COMMENT_NO = #{commentNo}")
	int isCommentReported(int user_no, int commentNo);

	@Update("UPDATE COMMENT SET COMMENT_REPORT_CNT = COMMENT_REPORT_CNT + 1 WHERE COMMENT_NO = #{commentNo}")
	void incrementCommentReportCount(int commentNo);

	@Insert("INSERT INTO COMMENT_REPORT (USER_NO, COMMENT_NO) VALUES (#{userNo}, #{commentNo})")
	void reportComment(CommentReportDto reportDto);

	@Results({@Result(property = "boardNo", column = "BOARD_NO"),
			@Result(property = "boardTitle", column = "BOARD_TITLE"),
			@Result(property = "boardContent", column = "BOARD_CONTENT"),
			@Result(property = "userNo", column = "USER_NO"),
			@Result(property = "boardReportCnt", column = "BOARD_REPORT_CNT"),
			@Result(property = "boardWriteDate", column = "BOARD_WRITE_DATE"),
			@Result(property = "boardViewCnt", column = "BOARD_VIEW_CNT")})
	@Select("SELECT b.*, u.USER_NAME " +
			"FROM COMMUNITY_BOARD b " +
			"JOIN USER u ON b.USER_NO = u.USER_NO " +
			"WHERE u.USER_ID = #{userId} " +
			"ORDER BY BOARD_WRITE_DATE DESC")
	List<CommunityDto> getUserCommunityList(String userId);
}





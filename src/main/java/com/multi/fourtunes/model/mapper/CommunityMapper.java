package com.multi.fourtunes.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.multi.fourtunes.model.dto.CommunityDto;

@Mapper
public interface CommunityMapper {

	@Results({ @Result(property = "boardNo", column = "BOARD_NO"),
			@Result(property = "boardTitle", column = "BOARD_TITLE"),
			@Result(property = "boardContent", column = "BOARD_CONTENT"),
			@Result(property = "userNo", column = "USER_NO"),
			@Result(property = "boardReportCnt", column = "BOARD_REPORT_CNT"),
			@Result(property = "boardWriteDate", column = "BOARD_WRITE_DATE"),
			@Result(property = "boardViewCnt", column = "BOARD_VIEW_CNT") })
	@Result(property = "user_name", column = "USER_NAME")
	@Select("SELECT b.*, u.USER_NAME " +
	        "FROM COMMUNITY_BOARD b " +   
	        "JOIN USER u ON b.USER_NO = u.USER_NO " +   
	        "ORDER BY BOARD_WRITE_DATE DESC") 
	List<CommunityDto> getAll();

	@Results({ @Result(property = "boardNo", column = "BOARD_NO"),
			@Result(property = "boardTitle", column = "BOARD_TITLE"),
			@Result(property = "boardContent", column = "BOARD_CONTENT"),
			@Result(property = "userNo", column = "USER_NO"),
			@Result(property = "boardReportCnt", column = "BOARD_REPORT_CNT"),
			@Result(property = "boardWriteDate", column = "BOARD_WRITE_DATE"),
			@Result(property = "boardViewCnt", column = "BOARD_VIEW_CNT") })
	@Select("SELECT b.BOARD_NO, b.BOARD_TITLE, b.BOARD_CONTENT, b.USER_NO, b.BOARD_REPORT_CNT, b.BOARD_WRITE_DATE, b.BOARD_VIEW_CNT, u.USER_NAME " +
	        "FROM COMMUNITY_BOARD b " +
	        "JOIN USER u ON b.USER_NO = u.USER_NO " +
	        "WHERE b.BOARD_NO = #{boardNo}")
	CommunityDto get(int boardNo);

	// 조회수
	@Update("UPDATE COMMUNITY_BOARD SET BOARD_VIEW_CNT = BOARD_VIEW_CNT + 1 WHERE BOARD_NO = #{boardNo}")
	int incrementViewCount(int boardNo);

	@Results({ @Result(property = "boardNo", column = "BOARD_NO"),
			@Result(property = "boardTitle", column = "BOARD_TITLE"),
			@Result(property = "boardContent", column = "BOARD_CONTENT"),
			@Result(property = "userNo", column = "USER_NO"),
			@Result(property = "boardReportCnt", column = "BOARD_REPORT_CNT"),
			@Result(property = "boardWriteDate", column = "BOARD_WRITE_DATE"),
			@Result(property = "boardViewCnt", column = "BOARD_VIEW_CNT") })
	@Insert("INSERT INTO COMMUNITY_BOARD (BOARD_TITLE, BOARD_CONTENT, USER_NO, BOARD_WRITE_DATE, BOARD_VIEW_CNT) "
	        + "VALUES (#{boardTitle}, #{boardContent}, #{userNo}, NOW(), #{boardViewCnt})")
	int insert(CommunityDto community);
	
	int update(CommunityDto community);

	int delete(int boardNo);
	
	
	@Results({ @Result(property = "boardNo", column = "BOARD_NO"),
		@Result(property = "boardTitle", column = "BOARD_TITLE"),
		@Result(property = "boardContent", column = "BOARD_CONTENT"),
		@Result(property = "userNo", column = "USER_NO"),
		@Result(property = "boardReportCnt", column = "BOARD_REPORT_CNT"),
		@Result(property = "boardWriteDate", column = "BOARD_WRITE_DATE"),
		@Result(property = "boardViewCnt", column = "BOARD_VIEW_CNT") })
	@Select("SELECT b.*, u.USER_NAME " +
	        "FROM COMMUNITY_BOARD b " +   
	        "JOIN USER u ON b.USER_NO = u.USER_NO " +   
	        "WHERE u.USER_ID = #{userId} " +
	        "ORDER BY BOARD_WRITE_DATE DESC") 
	List<CommunityDto> getUserCommunityList(String userId);
	
	
	//내가 작성할 게시글
	@Results({ @Result(property = "boardNo", column = "BOARD_NO"),
		@Result(property = "boardTitle", column = "BOARD_TITLE"),
		@Result(property = "boardContent", column = "BOARD_CONTENT"),
		@Result(property = "userNo", column = "USER_NO"),
		@Result(property = "boardWriteDate", column = "BOARD_WRITE_DATE"),
		@Result(property = "boardViewCnt", column = "BOARD_VIEW_CNT") })
	@Select("SELECT u.USER_NO, b.BOARD_NO, b.BOARD_TITLE, b.BOARD_CONTENT,b.BOARD_WRITE_DATE, b.BOARD_VIEW_CNT " + 
			" FROM COMMUNITY_BOARD b " +
			" JOIN USER u  ON u.USER_NO = b.USER_NO " + 
			" WHERE b.USER_NO = #{userNo} " ) 
	List<CommunityDto> getUserMyContentAll(int userNo);
}

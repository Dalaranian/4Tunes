package com.multi.fourtunes.model.mapper;

import com.multi.fourtunes.model.dto.CommentDto;
import com.multi.fourtunes.model.dto.CommunityDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MyPageMapper {
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

    @Results({@Result(property = "commentNo", column = "COMMENT_NO"),
            @Result(property = "boardNo", column = "BOARD_NO"),
            @Result(property = "boardTitle", column = "BOARD_TITLE"),
            @Result(property = "userNo", column = "USER_NO"),
            @Result(property = "commentContent", column = "COMMENT_CONTENT"),
            @Result(property = "commentReportCnt", column = "COMMENT_REPORT_CNT"),
            @Result(property = "user_name", column = "USER_NAME")})
    @Select(" SELECT b.USER_NO, c.BOARD_NO, b.BOARD_TITLE, c.COMMENT_CONTENT " +
            " FROM COMMENT  c JOIN COMMUNITY_BOARD b ON b.USER_NO = c.USER_NO " +
            " WHERE c.USER_NO = #{userNo} ")
    List<CommentDto> getComments(int userNo);
}

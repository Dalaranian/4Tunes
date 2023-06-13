package com.multi.fourtunes.model.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface KeywordMapper {

	@Select(" SELECT KEYWORD_NAME FROM KEYWORD ")
	public String[] getAllKeyword();

	@Insert(" INSERT INTO MANAGE_KEYWORD VALUES((SELECT USER_NO FROM USER WHERE USER_ID=#{userId}), (SELECT KEYWORD_NO FROM KEYWORD WHERE KEYWORD_NAME=#{keyword})) ")
	public int insertKeyword(String keyword, String userId);
	
	@Select(" SELECT k.KEYWORD_NAME FROM USER u JOIN MANAGE_KEYWORD mk ON u.USER_NO = mk.USER_NO JOIN KEYWORD k ON mk.KEYWORD_NO = k.KEYWORD_NO WHERE u.USER_NO =  #{userNo} ")
	public String[] getUserKeyword(int userNo);
	
	@Delete("DELETE FROM MANAGE_KEYWORD WHERE USER_NO = (SELECT USER_NO FROM USER WHERE USER_ID = #{userId})")
	public int deleteUserKeyword(String userId);
	
	@Insert(" INSERT INTO MANAGE_KEYWORD VALUES((SELECT USER_NO FROM USER WHERE USER_ID=#{userId}), (SELECT KEYWORD_NO FROM KEYWORD WHERE KEYWORD_NAME=#{keyword})) ")
	public int updateUserKeyword(String keyword, String userId);
}

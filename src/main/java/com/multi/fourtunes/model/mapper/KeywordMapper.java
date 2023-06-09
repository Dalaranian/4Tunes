package com.multi.fourtunes.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface KeywordMapper {

	@Select(" SELECT KEYWORD_NAME FROM KEYWORD ")
	public String[] getAllKeyword();

	@Insert(" INSERT INTO MANAGE_KEYWORD VALUES((SELECT USER_NO FROM USER WHERE USER_ID=#{userId}), (SELECT KEYWORD_NO FROM KEYWORD WHERE KEYWORD_NAME=#{keyword})) ")
	public int insertKeyword(String keyword, String userId);
}

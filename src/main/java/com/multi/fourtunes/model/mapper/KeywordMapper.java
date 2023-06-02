package com.multi.fourtunes.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface KeywordMapper {
 
	@Select(" SELECT KEYWORD_NAME FROM KEYWORD ")
	public String[] getAllKeyword();
}

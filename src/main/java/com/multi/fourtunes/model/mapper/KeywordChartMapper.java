package com.multi.fourtunes.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.multi.fourtunes.model.dto.KeywordChartDto;

@Mapper
public interface KeywordChartMapper {

	@Select("SELECT k.KEYWORD_NAME as keywordName, COUNT(*) as count FROM MANAGE_KEYWORD mk JOIN KEYWORD k ON mk.KEYWORD_NO = k.KEYWORD_NO GROUP BY mk.KEYWORD_NO")
    List<KeywordChartDto> getKeywordChart();
}
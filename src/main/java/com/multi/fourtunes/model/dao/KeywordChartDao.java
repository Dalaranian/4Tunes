package com.multi.fourtunes.model.dao;

import java.util.List;

import com.multi.fourtunes.model.dto.KeywordChartDto;

public interface KeywordChartDao {
	List<KeywordChartDto> getKeywordChart();
}

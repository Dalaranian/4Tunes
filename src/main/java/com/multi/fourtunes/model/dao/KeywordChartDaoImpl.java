package com.multi.fourtunes.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.dto.KeywordChartDto;
import com.multi.fourtunes.model.mapper.KeywordChartMapper;

@Repository
public class KeywordChartDaoImpl implements KeywordChartDao {
	
	@Autowired
	KeywordChartMapper keywordChartMapper;

	@Override
	public List<KeywordChartDto> getKeywordChart() {
		return keywordChartMapper.getKeywordChart();
	}

}

package com.multi.fourtunes.model.biz;

import java.util.List;

import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.multi.fourtunes.model.dto.AnalysisKeywordDto;

public interface AnalysisBiz {

	List<AnalysisKeywordDto> getAIKeywordAnalysis(int userNo);

	void updateSongAiKeyword();

}

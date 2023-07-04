package com.multi.fourtunes.model.biz;

import java.util.List;

import org.springframework.ui.Model;

import com.multi.fourtunes.model.dto.AnalysisKeywordDto;

public interface AnalysisBiz {

	List<AnalysisKeywordDto> getAIKeywordAnalysis(int userNo);

}

package com.multi.fourtunes.model.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.fourtunes.model.dao.KeywordChartDao;
import com.multi.fourtunes.model.dto.KeywordChartDto;

@Service
public class KeywordChartBizImpl implements KeywordChartBiz {

    @Autowired
    KeywordChartDao keywordChartDao;

    @Override
    public List<KeywordChartDto> getKeywordChart() {
        return keywordChartDao.getKeywordChart();
    }
}

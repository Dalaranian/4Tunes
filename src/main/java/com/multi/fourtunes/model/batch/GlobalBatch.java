package com.multi.fourtunes.model.batch;

import com.multi.fourtunes.model.biz.AnalysisBiz;
import com.multi.fourtunes.model.biz.SuggestBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GlobalBatch {

    @Autowired
    SuggestBiz suggestBiz;
    @Autowired
    AnalysisBiz analysisBiz;
    

    // 매월 1일 0시 0분 실행
    @Scheduled(cron = "0 0 0 1 * ?")
    public void executeTask() {
        log.info("모든 유저의 SuggestCount 를 초기화합니다. ");

        suggestBiz.seggestCountReset();
    }
    
    // 매일 오후 12시 실행
    @Scheduled(cron = "0 0 * * * ?")
    public void updateSongAiKeywordTask() {
    	log.info("NULL 값인 Aikeyword 를 다시 추천 받습니다. ");
    	
    	analysisBiz.updateSongAiKeyword();
    }
}


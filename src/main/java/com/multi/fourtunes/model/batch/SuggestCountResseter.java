package com.multi.fourtunes.model.batch;

import com.multi.fourtunes.model.biz.SuggestBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SuggestCountResseter {

    @Autowired
    SuggestBiz suggestBiz;

    // 매월 1일 0시 0분 실행
    @Scheduled(cron = "0 0 0 1 * ?")
    public void executeTask() {
        // 여기에 실행할 로직을 작성
        System.out.println("모든 유저의 SuggestCount 를 초기화합니다. ");

        suggestBiz.seggestCountReset();
    }
}

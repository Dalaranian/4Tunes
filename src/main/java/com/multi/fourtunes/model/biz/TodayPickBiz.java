package com.multi.fourtunes.model.biz;

import java.util.ArrayList;

import com.multi.fourtunes.model.dto.SongDto;

public interface TodayPickBiz {

	String getTodayPickName(int todayPickNo);

	ArrayList<SongDto> selectTodayPickList(int todayPickNo);

}

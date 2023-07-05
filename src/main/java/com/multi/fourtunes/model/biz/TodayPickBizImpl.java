package com.multi.fourtunes.model.biz;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.fourtunes.model.dao.TodayPickDao;
import com.multi.fourtunes.model.dto.SongDto;

@Service
public class TodayPickBizImpl implements TodayPickBiz {
	
	@Autowired
	TodayPickDao todayPickDao;

	@Override
	public String getTodayPickName(int todayPickNo) {
		return todayPickDao.getTodayPickName(todayPickNo);
	}

	@Override
	public ArrayList<SongDto> selectTodayPickList(int todayPickNo) {
		return todayPickDao.selectTodayPickList(todayPickNo);
	}

}

package com.multi.fourtunes.model.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.mapper.TodayPickMapper;

@Repository
public class TodayPickDaoImpl implements TodayPickDao{

	@Autowired
	TodayPickMapper todayPickMapper;
	
	@Override
	public String getTodayPickName(int todayPickNo) {
		return todayPickMapper.getTodayPickName(todayPickNo);
	}

	@Override
	public ArrayList<SongDto> selectTodayPickList(int todayPickNo) {
		return todayPickMapper.selectTodayPickList(todayPickNo);
	}

}

package com.multi.fourtunes.model.dao;

import java.util.ArrayList;

import com.multi.fourtunes.model.dto.SongDto;

public interface TodayPickDao {

	String getTodayPickName(int todayPickNo);

	ArrayList<SongDto> selectTodayPickList(int todayPickNo);

}

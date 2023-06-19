package com.multi.fourtunes.model.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.fourtunes.model.dao.ChartDao;
import com.multi.fourtunes.model.dto.SongDto;

@Service
public class ChartBizImpl {

	@Autowired
	private ChartDao chartDao;

	public List<SongDto> getTopSongs() {
		List<SongDto> topSongs = chartDao.getTopSongsByPopularity(10);
		return topSongs;
	}
}

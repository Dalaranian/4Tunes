package com.multi.fourtunes.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.mapper.ChartMapper;

@Repository
public class ChartDaoImpl implements ChartDao {
	@Autowired
	private ChartMapper chartMapper;

	@Override
	public List<SongDto> getTop10Songs() {
		return chartMapper.getTop10Songs();
	}
}

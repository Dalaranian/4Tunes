package com.multi.fourtunes.model.dao;

import java.util.List;

import com.multi.fourtunes.model.dto.SongDto;

public interface ChartDao {

	 List<SongDto> getTop10Songs();
}

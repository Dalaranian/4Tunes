package com.multi.fourtunes.model.biz;

import java.util.List;

import com.multi.fourtunes.model.dto.SongDto;

public interface ChartBiz {

	List<SongDto> getTop10Chart();
}

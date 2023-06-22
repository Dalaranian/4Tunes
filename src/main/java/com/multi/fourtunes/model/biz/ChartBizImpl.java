package com.multi.fourtunes.model.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.fourtunes.model.dao.ChartDao;
import com.multi.fourtunes.model.dto.SongDto;

@Service
public class ChartBizImpl implements ChartBiz {

	@Autowired
	private ChartDao chartDao;

	@Override
	public List<SongDto> getTop10Songs() {
		return chartDao.getTop10Songs();
	}

}

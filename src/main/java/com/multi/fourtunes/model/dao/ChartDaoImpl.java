package com.multi.fourtunes.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.mapper.ChartMapper;

@Repository
public class ChartDaoImpl implements ChartDao {
    private ChartMapper chartMapper;

    @Override
    public List<SongDto> getTopSongsByPopularity(int limit) {
        return chartMapper.getTopSongsByPopularity(limit);
    }
}

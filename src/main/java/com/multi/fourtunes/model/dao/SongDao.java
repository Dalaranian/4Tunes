package com.multi.fourtunes.model.dao;

import com.multi.fourtunes.model.dto.SongDto;

import java.util.List;

public interface SongDao {
    SongDto SelectSongById(String id);

    List<SongDto> selectSongListByPlayListNo(int playListNo, int userNo);
}

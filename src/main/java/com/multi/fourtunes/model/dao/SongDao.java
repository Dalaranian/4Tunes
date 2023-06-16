package com.multi.fourtunes.model.dao;

import com.multi.fourtunes.model.dto.SongDto;

public interface SongDao {
    SongDto SelectSongById(String id);
}

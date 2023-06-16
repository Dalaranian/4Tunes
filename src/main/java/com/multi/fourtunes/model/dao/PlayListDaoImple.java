package com.multi.fourtunes.model.dao;

import com.multi.fourtunes.model.mapper.ManageSongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PlayListDaoImple implements PlaylistDao{

    @Autowired
    ManageSongMapper manageSongMapper;

    @Override
    public int insertPlaylist(String playListNo, String songNo) {
        return manageSongMapper.insertSong(playListNo, songNo);
    }
}

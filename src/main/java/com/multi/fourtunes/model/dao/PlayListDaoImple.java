package com.multi.fourtunes.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.dto.PlaylistDto;
import com.multi.fourtunes.model.mapper.ManageSongMapper;
import com.multi.fourtunes.model.mapper.PlayListMapper;

@Repository
public class PlayListDaoImple implements PlaylistDao{

    @Autowired
    ManageSongMapper manageSongMapper;

    @Autowired
    PlayListMapper playListMapper;
    @Override
    public int insertPlaylist(String playListNo, String songNo) {
        return manageSongMapper.insertSong(playListNo, songNo);
    }

    @Override
    public void allocatePlaylist(int userNo) {
        playListMapper.allocatePlaylist(userNo, userNo+" 번 User의 PlayList");
    }

    @Override
    public List<PlaylistDto> selectAll() {
        return playListMapper.selectAllPlaylists();
    }
}

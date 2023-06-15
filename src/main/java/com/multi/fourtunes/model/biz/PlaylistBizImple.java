package com.multi.fourtunes.model.biz;

import com.multi.fourtunes.model.dao.SongDao;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistBizImple implements PlaylistBiz{

    @Autowired
    SongDao songDao;

    @Autowired
    SongRepository songRepository;

    @Override
    public String insertPlaylist(SongDto song, UserDto user) {

        // 추가하려는 노래가 DB 에 존재하는지 확인
        // SongDto selectSong = songDao.SelectSongById(song.getSongId());
        try {
            SongEntity selectSong = songRepository.findBySongId(song.getSongId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

package com.multi.fourtunes.model.biz;

import com.multi.fourtunes.model.dao.SongDao;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;
import org.apache.ibatis.annotations.Select;
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

        String result;
        String songId = song.getSongId();

        System.out.println(song);

        // 추가하려는 노래가 DB 에 존재하는지 확인
        // SongDto selectSong = songDao.SelectSongById(song.getSongId());
        try {
            SongEntity selectSong = songRepository.findBySongId(song.getSongId());
            // 노래가 없을시, DB 에 우선 저장
            if(selectSong == null){
                // entity 준비
                selectSong.setSongArtist(song.getSongArtist());
                selectSong.setSongLink(song.getSongLink());
                selectSong.setSongTitle(song.getSongTitle());
                selectSong.setSongId(song.getSongId());
                // entity 저장
                songRepository.save(selectSong);
                // 저장되어서, Auto Increment 값이 반영된 새로운 엔티티 불러오기
                selectSong = songRepository.findBySongId(song.getSongId());
            }
            System.out.println("select Song is : " + selectSong);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

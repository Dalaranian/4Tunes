package com.multi.fourtunes.model.biz;

import com.multi.fourtunes.model.dao.PlaylistDao;
import com.multi.fourtunes.model.dao.SongDao;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;
import com.multi.fourtunes.model.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class PlaylistBizImple implements PlaylistBiz{

    @Autowired
    SongDao songDao;

    @Autowired
    SongRepository songRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PlaylistDao playlistDao;

    @Override
    public String insertPlaylist(SongDto song, UserDto user) {
        String result;

//        System.out.println(song + "\n" + user);

        // 추가하려는 노래가 DB 에 존재하는지 확인
        // SongDto selectSong = songDao.SelectSongById(song.getSongId());
        try {
            SongEntity selectSong = songRepository.findBySongId(song.getSongId());
            // 노래가 없을시, DB 에 우선 저장
            if(selectSong == null){

                // 빈 SongEntity 생성
                selectSong = new SongEntity();

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

            // User 의 PlayListNo 를 조회
            String[] userPlayList = userMapper.getUserPlatListNo(Integer.toString(user.getUser_no()));

            // 첫 번째 PlayList 에 노래 넣기
            playlistDao.insertPlaylist(userPlayList[0], Long.toString(selectSong.getSongNo()));

            result = " 플레이리스트 저장에 성공했습니다. ";
        } catch (Exception e) {
//            e.printStackTrace();
            result = " 플레이리스트 저장에 실패했습니다. 다시 시도해주세요 ";
        }

        return result;
    }

    @Override
    public String insertJoinPlaylist(String playlistName, UserDto user, String playlistVisibility) {

        return playlistDao.insertJoinPlaylist(playlistName, user ,playlistVisibility);
    }

}

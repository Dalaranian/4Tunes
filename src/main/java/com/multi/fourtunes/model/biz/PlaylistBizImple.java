package com.multi.fourtunes.model.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.multi.fourtunes.model.dao.PlaylistDao;
import com.multi.fourtunes.model.dto.PlaylistDto;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.entity.UserEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;
import com.multi.fourtunes.model.jpa.repository.UserRepository;
import com.multi.fourtunes.model.mapper.PlayListMapper;
import com.multi.fourtunes.model.mapper.UserMapper;

@Service
public class PlaylistBizImple implements PlaylistBiz{

    @Autowired
    UserRepository userRepository;
    @Autowired
    SongRepository songRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PlayListMapper playListMapper;
    @Autowired
    PlaylistDao playlistDao;

    /**
     *
     * SongDto 를 받아서, 유저의 첫번째 PlayList 에 저장함.
     *
     * @param song 저장할 SongDto
     * @param user 저장할 User
     * @return 성공여부에 대해서, alert 로 띄워줄 Message
     */

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
                selectSong.setSongAlbumart(song.getSongAlbumArt());
                // entity 저장
                songRepository.save(selectSong);
                // 저장되어서, Auto Increment 값이 반영된 새로운 엔티티 불러오기
                selectSong = songRepository.findBySongId(song.getSongId());
            }

            // User 의 PlayListNo 를 조회
            String[] userPlayList = userMapper.getUserPlatListNo(Integer.toString(user.getUser_no()));

            // 첫 번째 PlayList 에 노래 넣기
            int res = playlistDao.insertPlaylist(userPlayList[0], Long.toString(selectSong.getSongNo()));

            result = " 플레이리스트 저장에 성공했습니다. ";
        } catch (DuplicateKeyException e) {
            result = song.getSongTitle() + " 는 이미 저장된 노래입니다. ";
        } catch (Exception e){
            result = " 플레이리스트 저장에 실패했습니다. 다시 시도해주세요 ";
        }

        return result;
    }

    @Override
    public void allocatePlaylist(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        playlistDao.allocatePlaylist(user.getUserNo());
    }
    
    @Override
    public List<PlaylistDto> getAllPlaylists() {
        List<PlaylistDto> playlists = playlistDao.selectAll();
        
        // 각 PlaylistDto의 albumImage 설정
        for(PlaylistDto playlist : playlists) {
            SongDto song = playListMapper.selectMostRecentSongAlbumArtInPlaylist(playlist.getPlaylistNo());
            if (song == null) {
                playlist.setAlbumArt("https://images.unsplash.com/photo-1682687980918-3c2149a8f110?ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1171&q=80");
            } else {
                playlist.setAlbumArt(song.getSongAlbumArt());
            }
        }
        
        return playlists;
    }

    @Override
    public List<PlaylistDto> getMyPlaylist(int myno) {
        List<PlaylistDto> myPlaylists = playListMapper.selectMine(myno);
        
        // 각 PlaylistDto의 albumImage 설정
        for(PlaylistDto playlist : myPlaylists) {
            SongDto song = playListMapper.selectMostRecentSongAlbumArtInPlaylist(playlist.getPlaylistNo());
            if (song == null) {
                playlist.setAlbumArt("https://images.unsplash.com/photo-1682687980918-3c2149a8f110?ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1171&q=80");
            } else {
                playlist.setAlbumArt(song.getSongAlbumArt());
            }
        }
        
        return myPlaylists;
    }

    
}

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

	@Override
	public List<PlaylistDto> selectMine(int userNo) {
		return playListMapper.selectMine(userNo);
	}

    /**
     * @param userNo
     * @return userNo 에 해당하는 playListno 의 배열을 리턴
     */
    @Override
    public int[] getPlayListNo(String userNo) {
        return playListMapper.getPlayListNo(userNo);
    }

    @Override
    public int deleteMyPlayList(String playListNo, Long songNo) {
//        System.out.println("playlist : " + playListNo + " " + "songNo : " + songNo);
        return playListMapper.deleteMyPlayList(playListNo, songNo);
    }

    @Override
    public String getPlayListVisibility(int userNo) {
        return playListMapper.getPlayListVisibility(userNo);
    }
}

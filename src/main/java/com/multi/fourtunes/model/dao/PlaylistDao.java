package com.multi.fourtunes.model.dao;

import java.util.List;

import com.multi.fourtunes.model.dto.PlaylistDto;


public interface PlaylistDao {

    int insertPlaylist(String playListNo, String songNo);
    void allocatePlaylist(int userNo);
    List<PlaylistDto> selectAll();
    List<PlaylistDto> selectMine(int userNo);
    int[] getPlayListNo(String userNo);

    int deleteMyPlayList(String playListNo, Long songNo);
}

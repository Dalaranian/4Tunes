package com.multi.fourtunes.model.biz;

import java.util.List;

import com.multi.fourtunes.model.dto.PlaylistDto;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;

public interface PlaylistBiz {

    String insertPlaylist(SongDto song, UserDto user);

    void allocatePlaylist(String userId);
    
    List<PlaylistDto> getAllPlaylists();
    
    List<PlaylistDto> getMyPlaylist(int myno);

    List<SongDto> getPlayListSongs(String userNo);
}

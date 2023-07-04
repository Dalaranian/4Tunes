package com.multi.fourtunes.model.biz;

import java.util.List;

import com.multi.fourtunes.model.dto.PlaylistDto;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.jpa.entity.SongEntity;

import javax.servlet.http.HttpSession;

public interface PlaylistBiz {

    String insertPlaylist(SongDto song, UserDto user);

    void allocatePlaylist(String userId);
    
    List<PlaylistDto> getAllPlaylists();
    
    List<PlaylistDto> getMyPlaylist(int myno);

    List<SongDto> getPlayListSongs(String userNo);

    String deleteMyPlaylist(SongEntity song, UserDto currentUser);

    String visibilityManage(UserDto currentLogin, boolean request);

    List<SongDto> getSongDtos(String requestJsonString, HttpSession session);
}

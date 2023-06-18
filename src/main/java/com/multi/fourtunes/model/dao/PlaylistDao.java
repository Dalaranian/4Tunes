package com.multi.fourtunes.model.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistDao {

    int insertPlaylist(String playListNo, String songNo);

    int insertJoinPlaylist(String playlistName, int userNo, String playlistVisibility);

}

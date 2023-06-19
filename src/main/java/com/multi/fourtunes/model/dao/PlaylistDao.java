package com.multi.fourtunes.model.dao;

import org.springframework.stereotype.Repository;


public interface PlaylistDao {

    int insertPlaylist(String playListNo, String songNo);

    void allocatePlaylist(int userNo);

}

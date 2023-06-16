package com.multi.fourtunes.model.biz;

import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;

public interface PlaylistBiz {

    String insertPlaylist(SongDto song, UserDto user);

}

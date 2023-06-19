package com.multi.fourtunes.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlayListMapper {
    @Insert(" INSERT INTO PLAYLIST VALUES(NULL, #{playListName}, #{userNo}, 'N')")
    void allocatePlaylist(int userNo, String playListName);
}

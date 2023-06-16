package com.multi.fourtunes.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManageSongMapper {

    @Insert(" INSERT INTO MANAGE_SONG VALUES(#{playlistNo}, #{userNo}) ")
    int insertSong(String playlistNo, String userNo);
}

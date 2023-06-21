package com.multi.fourtunes.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.multi.fourtunes.model.dto.PlaylistDto;

@Mapper
public interface PlayListMapper {
    @Insert(" INSERT INTO PLAYLIST VALUES(NULL, #{playListName}, #{userNo}, 'N')")
    void allocatePlaylist(int userNo, String playListName);
    
    @Results({ 
        @Result(property = "playlistNo", column = "PLAYLIST_NO"),
        @Result(property = "playlistName", column = "PLAYLIST_NAME"),
        @Result(property = "userNo", column = "USER_NO"),
        @Result(property = "playlistVisibility", column = "PLAYLIST_VISIBILITY") 
      })
    @Select("SELECT * FROM PLAYLIST")
    List<PlaylistDto> selectAllPlaylists();
}

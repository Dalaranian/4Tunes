package com.multi.fourtunes.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.multi.fourtunes.model.dto.PlaylistDto;
import com.multi.fourtunes.model.dto.SongDto;

@Mapper
public interface PlayListMapper {
    @Insert(" INSERT INTO PLAYLIST VALUES(NULL, #{playListName}, #{userNo}, 'N')")
    void allocatePlaylist(int userNo, String playListName);
    
    @Results({ 
        @Result(property = "playlistNo", column = "PLAYLIST_NO"),
        @Result(property = "playlistName", column = "PLAYLIST_NAME"),
        @Result(property = "userNo", column = "USER_NO"),
        @Result(property = "playlistVisibility", column = "PLAYLIST_VISIBILITY"),
        @Result(property = "albumImage", column = "SONG_ALBUMART"),
        @Result(property = "userName", column = "USER_NAME")
      })
    @Select("SELECT PLAYLIST.*, USER.USER_NAME FROM PLAYLIST " +
            "JOIN USER ON PLAYLIST.USER_NO = USER.USER_NO " +
            "WHERE PLAYLIST_VISIBILITY = 'Y'")
    List<PlaylistDto> selectAllPlaylists();
    
    @Results({
        @Result(property = "songNo", column = "SONG_NO"),
        @Result(property = "songTitle", column = "SONG_TITLE"),
        @Result(property = "songArtist", column = "SONG_ARTIST"),
        @Result(property = "songLink", column = "SONG_LINK"),
        @Result(property = "songId", column = "SONG_ID"),
        @Result(property = "songAlbumArt", column = "SONG_ALBUMART")
    })
    @Select("SELECT SONG.* FROM MANAGE_SONG " +
            "INNER JOIN SONG ON MANAGE_SONG.SONG_NO = SONG.SONG_NO " +
            "WHERE MANAGE_SONG.PLAYLIST_NO = #{playlistNo} " +
            "ORDER BY MANAGE_SONG.SONG_NO DESC LIMIT 1")
    SongDto selectMostRecentSongAlbumArtInPlaylist(int playlistNo);
    
    @Results({
        @Result(property = "playlistNo", column = "PLAYLIST_NO"),
        @Result(property = "playlistName", column = "PLAYLIST_NAME"),
        @Result(property = "userNo", column = "USER_NO"),
        @Result(property = "playlistVisibility", column = "PLAYLIST_VISIBILITY"),
        @Result(property = "userName", column = "USER_NAME")
    })
    @Select("SELECT PLAYLIST.*, USER.USER_NAME FROM PLAYLIST " +
            "JOIN USER ON PLAYLIST.USER_NO = USER.USER_NO " +
            "WHERE PLAYLIST.USER_NO = #{userNo}")
    List<PlaylistDto> selectMine(int userNo);
    
}

package com.multi.fourtunes.model.mapper;

import com.multi.fourtunes.model.dto.SongDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SongMapper {

    @Results({ @Result(property = "songNo", column = "SONG_NO"),
            @Result(property = "songTitle", column = "SONG_TITLE"),
            @Result(property = "songArtist", column = "SONG_ARTIST"),
            @Result(property = "songAlbumArt", column = "SONG_ALBUMART"),
            @Result(property = "songLink", column = "SONG_LINK"),
            @Result(property = "songId", column = "SONG_ID")
//            @Result(property = "playlistCount", column = "BOARD_VIEW_CNT")
    })
    @Select(" SELECT s.* FROM SONG s INNER JOIN MANAGE_SONG ms ON s.SONG_NO = ms.SONG_NO INNER JOIN PLAYLIST p ON p.PLAYLIST_NO = ms.PLAYLIST_NO WHERE p.USER_NO = #{userNo} AND p.PLAYLIST_NO = #{playListNo} ")
    List<SongDto> selectSongListByPlayListNo(int playListNo, int userNo);
}

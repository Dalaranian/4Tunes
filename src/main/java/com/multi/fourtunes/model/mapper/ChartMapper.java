package com.multi.fourtunes.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.multi.fourtunes.model.dto.SongDto;
import java.util.List;

@Mapper
public interface ChartMapper {
    @Select("SELECT s.SONG_NO, s.SONG_TITLE, s.SONG_ARTIST, s.SONG_LINK, s.SONG_ID " +
            "FROM SONG s " +
            "JOIN MANAGE_SONG ms ON s.SONG_NO = ms.SONG_NO " +
            "JOIN PLAYLIST p ON ms.PLAYLIST_NO = p.PLAYLIST_NO " +
            "WHERE p.PLAYLIST_VISIBILITY = 'PUBLIC' " +
            "GROUP BY s.SONG_NO " +
            "ORDER BY COUNT(*) DESC " +
            "LIMIT #{limit}")
    List<SongDto> getTopSongsByPopularity(int limit);
}

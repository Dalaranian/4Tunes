package com.multi.fourtunes.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import com.multi.fourtunes.model.dto.SongDto;
import java.util.List;

@Mapper
public interface ChartMapper {
//	@Results({ @Result(property = "songNo", column = "SONG_NO"),
//		@Result(property = "songTitle", column = "SONG_TITLE"),
//		@Result(property = "songArtist", column = "SONG_ARTIST"),
//		@Result(property = "songLink", column = "SONG_LINK"),
//		@Result(property = "songId", column = "SONG_ID") })
//    @Select("SELECT s.SONG_TITLE, s.SONG_ARTIST, s.SONG_LINK, s.SONG_ID " +
//            "FROM SONG s " +
//            "JOIN MANAGE_SONG ms ON s.SONG_NO = ms.SONG_NO " +
//            "GROUP BY s.SONG_NO " +
//            "ORDER BY COUNT(*) DESC " +
//            "LIMIT #{limit}")
//    List<SongDto> getTopSongsByPopularity(int limit);

	@Select("SELECT * FROM SONG ORDER BY PLAY_COUNT DESC LIMIT 10")
	List<SongDto> getTop10Chart();
}

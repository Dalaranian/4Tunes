package com.multi.fourtunes.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.ManageSongDto;
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

//	SELECT ms.SongNo, s.SongTitle, s.SongArtist, s.SongLink
//	FROM Manage_Song ms
//	JOIN Song s ON ms.SongNo = s.SongNo
//	ORDER BY ms.SongNo DESC
	
	
	  
	@Results({ @Result(property = "songNo", column = "SONG_NO"),
		@Result(property = "songTitle", column = "SONG_TITLE"),
		@Result(property = "songArtist", column = "SONG_ARTIST"),	
		@Result(property = "songLink", column = "SONG_LINK"),
		@Result(property = "songId", column = "SONG_ID"),
		@Result(property = "playlistNo", column = "PLAYLIST_NO")})		
	@Select("SELECT S.SONG_NO, S.SONG_TITLE, S.SONG_ARTIST, S.SONG_LINK " +
	        "FROM MANAGE_SONG MS " +
	        "INNER JOIN SONG S ON MS.SONG_NO = S.SONG_NO " +
	        "GROUP BY S.SONG_NO " +
	        "ORDER BY COUNT(*) DESC, S.SONG_TITLE " +
	        "LIMIT 10")
	List<SongDto> getTop10Songs();
	
	
	
	
	
}

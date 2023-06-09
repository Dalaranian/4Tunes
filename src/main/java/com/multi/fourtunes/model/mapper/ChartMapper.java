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
	  
	@Results({ @Result(property = "songNo", column = "SONG_NO"),
		@Result(property = "songTitle", column = "SONG_TITLE"),
		@Result(property = "songArtist", column = "SONG_ARTIST"),	
		@Result(property = "songLink", column = "SONG_LINK"),
		@Result(property = "songId", column = "SONG_ID")}) 
	@Select("SELECT S.SONG_NO, S.SONG_TITLE, S.SONG_ARTIST, S.SONG_LINK, S.SONG_ID " +
	        "FROM MANAGE_SONG MS " +
	        "INNER JOIN SONG S ON MS.SONG_NO = S.SONG_NO " +
	        "GROUP BY S.SONG_NO " +
	        "ORDER BY COUNT(*) DESC, S.SONG_TITLE " +
	        "LIMIT 10")
	List<SongDto> getTop10Songs();

	// 전체 플레이리스트 횟수 정보 추가
	@Select("SELECT COUNT(*) FROM MANAGE_SONG WHERE SONG_NO = #{songNo}")
	int getPlaylistCount(int songNo);
	
}

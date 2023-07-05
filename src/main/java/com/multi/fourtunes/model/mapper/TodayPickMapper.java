package com.multi.fourtunes.model.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.multi.fourtunes.model.dto.SongDto;

@Mapper
public interface TodayPickMapper {

	@Select(" SELECT 4TUNES_PLAYLIST_NAME_KO FROM 4TUNES_PLAYLIST WHERE 4TUNES_PLAYLIST_NO=#{todayPickNo} ")
	String getTodayPickName(int todayPickNo);

	@Results({ @Result(property = "songNo", column = "SONG_NO"),
		@Result(property = "songTitle", column = "SONG_TITLE"),
		@Result(property = "songArtist", column = "SONG_ARTIST"),	
		@Result(property = "songLink", column = "SONG_LINK"),
		@Result(property = "songId", column = "SONG_ID")})
	@Select(" SELECT s.* FROM SONG s JOIN MANAGE_4TUNES_PLAYLIST mtp ON s.SONG_NO=mtp.SONG_NO WHERE mtp.4TUNES_PLAYLIST_NO=#{todayPickNo} ")
	ArrayList<SongDto> selectTodayPickList(int todayPickNo);
	
}

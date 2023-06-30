package com.multi.fourtunes.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminpageMapper {

	@Insert(" INSERT INTO MANAGE_4TUNES_PLAYLIST VALUES(#{songNo}, (SELECT 4TUNES_PLAYLIST_NO FROM 4TUNES_PLAYLIST WHERE 4TUNES_PLAYLIST_NAME=#{playlist})) ")
	int insertSong(Long songNo, String playlist);

}

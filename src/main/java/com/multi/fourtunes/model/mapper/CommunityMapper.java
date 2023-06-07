package com.multi.fourtunes.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.multi.fourtunes.model.dto.CommunityDto;


@Mapper
public interface CommunityMapper {
	
	@Select("SELECT * FROM COMMUNITY_BOARD")
	List<CommunityDto> getAll();
}

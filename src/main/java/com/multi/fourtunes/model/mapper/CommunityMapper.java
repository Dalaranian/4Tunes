package com.multi.fourtunes.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.multi.fourtunes.model.dto.CommunityDto;

@Mapper
public interface CommunityMapper {

    @Select(" SELECT * FROM COMMUNITY_BOARD ")
    List<CommunityDto> getAll();
    
    @Select(" SELECT * FROM COMMUNITY_BOARD WHERE boardNo = #{boardNo} ")
    CommunityDto get(int boardNo);

	int insert(CommunityDto community);

	int update(CommunityDto community);

	int delete(int boardNo);
}

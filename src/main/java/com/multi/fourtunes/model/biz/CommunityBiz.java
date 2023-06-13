package com.multi.fourtunes.model.biz;

import java.util.List;

import com.multi.fourtunes.model.dto.CommunityDto;

public interface CommunityBiz {
	List<CommunityDto> getAll();

	CommunityDto get(int boardNo);

	void insert(CommunityDto community);

	void update(CommunityDto community);

	void delete(int boardNo);

	void incrementViewCount(int boardNo);
	
	List<CommunityDto> getUserMyContentAll(int userNo);
}

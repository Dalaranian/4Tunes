package com.multi.fourtunes.model.dao;

import java.util.List;

import com.multi.fourtunes.model.dto.CommunityDto;

public interface CommunityDao {
	public List<CommunityDto> getAll();

	public CommunityDto get(int boardNo);

	int insert(CommunityDto community);

	int update(CommunityDto community);

	int delete(int boardNo);
}
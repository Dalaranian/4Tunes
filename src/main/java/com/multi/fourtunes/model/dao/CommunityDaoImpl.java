package com.multi.fourtunes.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.mapper.CommunityMapper;

@Repository
public class CommunityDaoImpl implements CommunityDao {

	@Autowired
	private CommunityMapper communityMapper;

	@Override
	public List<CommunityDto> getAll() {
		return communityMapper.getAll();
	}

	@Override
	public CommunityDto get(int boardNo) {
		return communityMapper.get(boardNo);
	}

	@Override
	public int insert(CommunityDto community) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(CommunityDto community) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int boardNo) {
		// TODO Auto-generated method stub
		return 0;
	}
}

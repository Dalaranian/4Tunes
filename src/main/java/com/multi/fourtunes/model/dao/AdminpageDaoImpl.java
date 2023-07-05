package com.multi.fourtunes.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.mapper.AdminpageMapper;

@Repository
public class AdminpageDaoImpl implements AdminpageDao{

	@Autowired
	AdminpageMapper adminMapper;
	
	@Override
	public int insertSong(Long songNo, String playlist) {
		return adminMapper.insertSong(songNo, playlist);
	}

	

}

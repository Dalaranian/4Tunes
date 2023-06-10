package com.multi.fourtunes.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.mapper.KeywordMapper;

@Repository
public class keywordDaoImple implements KeywordDao {

	@Autowired
	private KeywordMapper keywordMapper;

	@Override
	public String[] getAllList() {
		// TODO Auto-generated method stub
		return keywordMapper.getAllKeyword();
	}

	@Override
	public int insertKeyword(String keyword, String userId) {
		return keywordMapper.insertKeyword(keyword, userId);
	}

}

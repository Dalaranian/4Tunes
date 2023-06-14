package com.multi.fourtunes.model.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.fourtunes.model.dao.KeywordDao;
import com.multi.fourtunes.model.dto.UserDto;

@Service
public class KeywordBizImpl implements KeywordBiz {
	
	@Autowired
	private KeywordDao keywordDao;

	@Override
	public int insertKeyword(String keyword, String userId) {
		return keywordDao.insertKeyword(keyword, userId);
	}

	@Override
	public int updateKeyword(String keyword, String userId) {
		return keywordDao.updateUser(keyword, userId);
	}

	@Override
	public int deleteUserKeyword(String userId) {
		return keywordDao.deleteUserKeyword(userId);
	}
	
}

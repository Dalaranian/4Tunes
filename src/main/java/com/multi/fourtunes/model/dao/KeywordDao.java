package com.multi.fourtunes.model.dao;

import com.multi.fourtunes.model.dto.UserDto;

public interface KeywordDao {

	String[] getAllList();

	public int insertKeyword(String keyword, String userId);

	String[] userKeyword(int userNo);
	
	public int updateUser(String keyword, String userId);
	
	public int deleteUserKeyword(String userId);
}

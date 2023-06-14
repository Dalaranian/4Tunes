package com.multi.fourtunes.model.biz;

import com.multi.fourtunes.model.dto.UserDto;

public interface KeywordBiz {

	public int insertKeyword(String keyword, String userId);
	
	public int updateKeyword(String keyword, String userNo);

	public int deleteUserKeyword(String userNo);
}

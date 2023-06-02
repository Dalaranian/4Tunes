package com.multi.fourtunes.model.dao;

import com.multi.fourtunes.model.dto.UserDto;

public interface UserDao {

	public UserDto login(UserDto dto);
	
	public UserDto selectUserByEmailAndId(String email, String userId);
}


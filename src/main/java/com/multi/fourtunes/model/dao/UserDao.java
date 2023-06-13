package com.multi.fourtunes.model.dao;

import java.util.List;

import com.multi.fourtunes.model.dto.UserDto;

public interface UserDao {

	public UserDto login(UserDto dto);
	
	public UserDto selectUserByEmailAndId(String email, String userId);

	public int insertUser(UserDto insert);
	
}


package com.multi.fourtunes.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.mapper.UserMapper;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDto login(UserDto dto) {
		UserDto res = null;
		res = userMapper.login(dto);
		return res;
	}

	@Override
	public UserDto selectUserByEmailAndId(String email, String userId) {
		UserDto res = null;
		res = userMapper.selectUserByEmailAndId(email, userId);
		return res;
	}

	@Override
	public int insertUser(UserDto insert) {
		return userMapper.insertUser(insert);
	}

	@Override
	public List<UserDto> selectList() {
		return userMapper.selectList();
	}
}

package com.multi.fourtunes.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.mapper.UserMapper;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private UserMapper memberMapper;
	
//	@Autowired
//	private SqlSessionTemplate sqlSession;
	
	@Override
	public UserDto login(UserDto dto) {
		UserDto res = null;
		
//		res = sqlSession.selectOne("login", dto);
		
		res = memberMapper.login(dto);
		
		System.out.println("dao 에서 받은 dto " + res.toString());
		
		return res;
	}


	@Override
	public UserDto selectUserByEmailAndId(String email, String userId) {
	    UserDto res = null;
	    res = memberMapper. selectUserByEmailAndId(email, userId);
	    return res;
	}
}	
	

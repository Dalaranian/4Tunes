package com.multi.fourtunes.model.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.fourtunes.model.dao.UserDao;
import com.multi.fourtunes.model.dto.UserDto;

@Service
public class UserBizImpl implements UserBiz {

	@Autowired
	private UserDao memberDao;

	public UserBizImpl(UserDao memberDao) {
		super();
		this.memberDao = memberDao;
	}
	
	
	@Override
	public UserDto login(UserDto dto) {
//		System.out.println("비즈 진입 + " + dto);
		
		return memberDao.login(dto);
	}
	
	
	
}

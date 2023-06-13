package com.multi.fourtunes.model.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.fourtunes.model.dao.UserDao;
import com.multi.fourtunes.model.dto.UserDto;

@Service
public class AdminpageBizImpl implements AdminpageBiz{

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<UserDto> selectList() {
		return userDao.selectList();
	}

}

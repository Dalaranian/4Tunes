package com.multi.fourtunes.model.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.fourtunes.model.dao.KeywordDao;
import com.multi.fourtunes.model.dao.UserDao;
import com.multi.fourtunes.model.dto.UserDto;

@Service
public class LoginBizImple implements LoginBiz {

	@Autowired
	private UserDao userDao;

	@Autowired
	private KeywordDao keywordDao;

	@Override
	public boolean checkUserExist(String email, String userId) {
		// 방금한거
		System.out.println("email : " + email + "\nuserId : " + userId);
		UserDto userDTO = userDao.selectUserByEmailAndId(email, userId);
		// 방금한거
		// System.out.println("LoginBizImple " + userDTO.toString());
		return (userDTO != null) ? true : false;
	}

	@Override
	public String[] getKeyword() {
		// TODO Auto-generated method stub
		return keywordDao.getAllList();
	}

	@Override
	public UserDto login(UserDto dto) {
		return userDao.login(dto);
	}

	@Override
	public UserDto socialLogin(UserDto loginUser) {
		// TODO Auto-generated method stub
		return userDao.selectUserByEmailAndId(loginUser.getUser_id(), loginUser.getUser_name());
	}

	@Override
	public int insertUser(UserDto insert) {
		return userDao.insertUser(insert);
	}

}

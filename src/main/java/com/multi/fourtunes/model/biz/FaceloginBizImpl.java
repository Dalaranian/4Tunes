package com.multi.fourtunes.model.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.fourtunes.model.dao.UserDao;
import com.multi.fourtunes.model.dto.UserDto;

@Service
public class FaceloginBizImpl implements FaceloginBiz {

	@Autowired
	private UserDao userDao;

	@Override
	public boolean checkUserExist(String email, String userId) {
		// DB에서 이메일과 사용자 아이디로 사용자 조회
		UserDto userDTO = userDao.selectUserByEmailAndId(email, userId);
		return userDTO != null;
	}
}
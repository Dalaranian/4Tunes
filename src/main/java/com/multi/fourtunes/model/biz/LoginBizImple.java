package com.multi.fourtunes.model.biz;

import com.multi.fourtunes.model.dao.RoleManageDao;
import com.multi.fourtunes.model.jpa.entity.UserEntity;
import com.multi.fourtunes.model.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.fourtunes.model.dao.KeywordDao;
import com.multi.fourtunes.model.dao.PayDao;
import com.multi.fourtunes.model.dao.UserDao;
import com.multi.fourtunes.model.dto.UserDto;
import java.util.Date;

@Service
public class LoginBizImple implements LoginBiz {

	@Autowired
	private UserDao userDao;

	@Autowired
	private KeywordDao keywordDao;

	@Autowired
	private PayDao payDao;

	@Autowired
	private RoleManageDao roleManageDao;

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	public LoginBizImple(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean checkUserExist(String email, String userId) {
		System.out.println("email : " + email + "\nuserId : " + userId);
		UserDto userDTO = userDao.selectUserByEmailAndId(email, userId);

		return (userDTO != null) ? true : false;
	}

	@Override
	public String[] getKeyword() {
		// TODO Auto-generated method stub
		return keywordDao.getAllList();
	}

	@Override
	public String getUserKeyword(int userNo) {
		String[] userKeyword = keywordDao.userKeyword(userNo);

		// StringBuilder : String을 합치는 역할, append()를 통해 문자열을 합쳐준다.
		StringBuilder myKeyword = new StringBuilder();
		for (String str : userKeyword) {
			myKeyword.append(str + " ");
		}

		return myKeyword.toString();
	}

	@Override
	public void insertUserRole(String userId) {
		UserEntity user = userRepository.findByUserId(userId);
		roleManageDao.insertUserRole(user.getUserNo());
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

	@Override
	public Date getSubscriptionEndDate(int user_no) {
		return payDao.getSubscriptionEndDate(user_no);
	}

	@Override
	public int getSubscriptionMonth(int user_no) {
		return payDao.getSubscriptionMonth(user_no);
	}

	@Override
	public boolean isValidEmail(String joinEmail) {

		UserEntity checkUserEntitiy = userRepository.findByUserId(joinEmail);

		try {
			System.out.println("isValidEmail : " + checkUserEntitiy.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userRepository.findByUserId(joinEmail) == null;
	}


}

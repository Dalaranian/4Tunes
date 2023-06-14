package com.multi.fourtunes.model.biz;

import com.multi.fourtunes.model.dto.UserDto;
import java.util.Date;

public interface LoginBiz {
	boolean checkUserExist(String email, String userId);

	String[] getKeyword();

	public UserDto login(UserDto dto);

	UserDto socialLogin(UserDto loginUser);

	public int insertUser(UserDto insert);

	String[] getUserKeyword(int userNo);

	Date getSubscriptionEndDate(int user_no);
	
    void insertUserRole(String userNo);
}
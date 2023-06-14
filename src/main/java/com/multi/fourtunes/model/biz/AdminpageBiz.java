package com.multi.fourtunes.model.biz;

import java.util.List;

import com.multi.fourtunes.model.dto.UserDto;

public interface AdminpageBiz {

	public List<UserDto> selectList();

	public String selectGrade(int user_no);

	public int updateGradePaid(int user_no);

	public int updateGradeFree(int user_no);

	public int deleteUser(int user_no);

	public List<UserDto> searchUser(String name);

}

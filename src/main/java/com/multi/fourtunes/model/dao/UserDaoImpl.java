package com.multi.fourtunes.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.mapper.KeywordMapper;
import com.multi.fourtunes.model.mapper.UserMapper;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private KeywordMapper keywordMapper;
	
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

	@Override
	public String selectGrade(int user_no) {
		return userMapper.selectGrade(user_no);
	}

	@Override
	public int updateGradePaid(int user_no) {
		return userMapper.updateGradePaid(user_no);
	}

	@Override
	public int updateGradeFree(int user_no) {
		return userMapper.updateGradeFree(user_no);
	}

	@Override
	public int deleteUser(int user_no) {
		return userMapper.deleteUser(user_no);
	}

	@Override
	public List<UserDto> searchUser(String name) {
		return userMapper.searchUser(name);
	}

	/**
	 * User의 고유번호를 받아서 해당하는 PlayListNo의 배열을 리턴
	 */
	@Override
	public String[] getUserPlaylistNo(String userNo) {
		return userMapper.getUserPlatListNo(userNo);
	}

}

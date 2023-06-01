package com.multi.fourtunes.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.multi.fourtunes.model.dto.UserDto;

@Mapper
public interface UserMapper {
	
	@Select(" SELECT * FROM USER WHERE USER_ID=#{user_id} AND USER_PW=#{user_pw} ")
	UserDto login(UserDto dto);

}

package com.multi.fourtunes.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.multi.fourtunes.model.dto.UserDto;

@Mapper
public interface UserMapper {

	@Select(" SELECT * FROM USER WHERE USER_ID=#{user_id} AND USER_PW=#{user_pw} ")
	UserDto login(UserDto dto);

	@Select("SELECT * FROM USER WHERE USER_ID = #{email} AND USER_NAME = #{userId}")
	UserDto selectUserByEmailAndId(String email, String userId);

	@Insert(" INSERT INTO USER VALUES(NULL, #{user_id}, #{user_name}, #{user_pw}, 'FREE') ")
	int insertUser(UserDto insert);

	@Select(" SELECT * FROM USER ")
	List<UserDto> selectList();

	@Select(" SELECT USER_GRADE FROM USER WHERE USER_NO=#{user_no} ")
	String selectGrade(int user_no);

	@Update(" UPDATE USER SET USER_GRADE='PAID' WHERE USER_NO=#{user_no} ")
	int updateGradePaid(int user_no);

	@Update(" UPDATE USER SET USER_GRADE='FREE' WHERE USER_NO=#{user_no} ")
	int updateGradeFree(int user_no);

	@Delete(" DELETE FROM USER WHERE USER_NO=#{user_no} ")
	int deleteUser(int user_no);

	@Select(" SELECT * FROM USER WHERE USER_NAME LIKE CONCAT('%',#{name},'%') ")
	List<UserDto> searchUser(String name);
	
	

}

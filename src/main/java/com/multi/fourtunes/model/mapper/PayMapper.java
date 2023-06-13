package com.multi.fourtunes.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.multi.fourtunes.model.dto.PayDto;

@Mapper
public interface PayMapper {

	@Insert(" INSERT INTO PAY VALUES(NULL, #{user_no}, NOW(), #{pay_price}) ")
	int insertPayInfo(PayDto insert);

	@Update(" UPDATE USER SET USER_GRADE='PAID' WHERE USER_NO=#{userNo} ")
	int updateUserGrade(int userNo);

}

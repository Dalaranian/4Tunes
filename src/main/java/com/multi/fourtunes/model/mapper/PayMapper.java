package com.multi.fourtunes.model.mapper;


import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.multi.fourtunes.model.dto.PayDto;

@Mapper
public interface PayMapper {

	@Insert(" INSERT INTO PAY VALUES(NULL, #{user_no}, NOW(), #{pay_price}) ")
	int insertPayInfo(PayDto insert);

	@Update(" UPDATE USER SET USER_GRADE='PAID' WHERE USER_NO=#{userNo} ")
	int updateUserGrade(int userNo);
	
	@Select(" SELECT DATE_ADD(PAY_DATE, INTERVAL 30 DAY) FROM PAY WHERE USER_NO = #{userNo} ")
	Date getSubscriptionEndDate(int userNo);
	
	@Select(" SELECT COUNT(USER_NO) FROM PAY WHERE USER_NO=#{userNo} ")
	int getSubscriptionMonth(int userNo);
}

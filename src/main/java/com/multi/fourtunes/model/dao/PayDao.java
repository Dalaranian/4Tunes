package com.multi.fourtunes.model.dao;

import java.util.Date;

import com.multi.fourtunes.model.dto.PayDto;

public interface PayDao {

	public int insertPayInfo(PayDto insert);

	public int updateUserGrade(int userNo);

	Date getSubscriptionEndDate(int user_no);

	public int getSubscriptionMonth(int user_no);

}

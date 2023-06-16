package com.multi.fourtunes.model.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.dto.PayDto;
import com.multi.fourtunes.model.mapper.PayMapper;

@Repository
public class PayDaoImpl implements PayDao {
	
	@Autowired
	private PayMapper payMapper;

	@Override
	public int insertPayInfo(PayDto insert) {
		return payMapper.insertPayInfo(insert);
	}

	@Override
	public int updateUserGrade(int userNo) {
		return payMapper.updateUserGrade(userNo);
	}
	
	@Override
	public Date getSubscriptionEndDate(int user_no) {
		return payMapper.getSubscriptionEndDate(user_no);
	}

	@Override
	public int getSubscriptionMonth(int user_no) {
		return payMapper.getSubscriptionMonth(user_no);
	}

}

package com.multi.fourtunes.model.dao;

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

}

package com.multi.fourtunes.model.dao;

import com.multi.fourtunes.model.dto.PayDto;

public interface PayDao {

	public int insertPayInfo(PayDto insert);

	public int updateUserGrade(int userNo);

}

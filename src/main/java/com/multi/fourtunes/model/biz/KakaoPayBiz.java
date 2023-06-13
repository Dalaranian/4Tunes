package com.multi.fourtunes.model.biz;

import com.multi.fourtunes.model.dto.ApproveDto;
import com.multi.fourtunes.model.dto.PayDto;
import com.multi.fourtunes.model.dto.ReadyDto;

public interface KakaoPayBiz {
	
	public ReadyDto payReady();
	
	public ApproveDto payApprove(String tid, String pg_token);
	
	public int insertPayInfo(PayDto insert);

	public int updateUserGrade(int userNo);

}

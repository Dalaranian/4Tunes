package com.multi.fourtunes.model.biz;

import com.multi.fourtunes.model.dao.CommunityDao;
import com.multi.fourtunes.model.dao.MyPageDao;
import com.multi.fourtunes.model.dto.CommunityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageBizImpl implements  MyPageBiz{
    @Autowired
    private MyPageDao myPageDao;

    @Override
    public List<CommunityDto> getUserMyContentAll(int userNo){
        return myPageDao.getUserMyContentAll(userNo);
    }
}

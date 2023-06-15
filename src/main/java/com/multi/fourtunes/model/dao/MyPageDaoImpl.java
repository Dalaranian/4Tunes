package com.multi.fourtunes.model.dao;

import com.multi.fourtunes.model.biz.MyPageBiz;
import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.mapper.MyPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MyPageDaoImpl implements MyPageDao{

    @Autowired
    private MyPageMapper myPageMapper;


    @Override
    public List<CommunityDto> getUserMyContentAll(int userNo){
        return myPageMapper.getUserMyContentAll(userNo);
    }
}

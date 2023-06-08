package com.multi.fourtunes.model.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.fourtunes.model.dao.CommunityDao;
import com.multi.fourtunes.model.dto.CommunityDto;

@Service
public class CommunityBizImpl implements CommunityBiz {
    private CommunityDao communityDao;

    @Autowired
    public CommunityBizImpl(CommunityDao communityDao) {
        this.communityDao = communityDao;
    }

    @Override
    @Transactional
    public List<CommunityDto> getAll() {
        return communityDao.getAll();
    }

    @Override
    public CommunityDto get(int boardNo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insert(CommunityDto community) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(CommunityDto community) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(int boardNo) {
        // TODO Auto-generated method stub

    }
}

package com.multi.fourtunes.model.biz;

import com.multi.fourtunes.model.dao.MyPageDao;
import com.multi.fourtunes.model.dto.CommentDto;
import com.multi.fourtunes.model.dto.CommunityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageBizImpl implements  MyPageBiz{
    @Autowired
    private MyPageDao myPageDao;
   
    // 내 게시글 조회
    @Override
    public List<CommunityDto> getUserMyContentAll(int userNo){
        return myPageDao.getUserMyContentAll(userNo);
    }
    // 내 댓글 조회
    @Override
    public List<CommentDto> getComments(int userNo) {
        return myPageDao.getComments(userNo);
    }
}

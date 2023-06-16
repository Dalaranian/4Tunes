package com.multi.fourtunes.model.dao;

import com.multi.fourtunes.model.dto.CommentDto;
import com.multi.fourtunes.model.dto.CommunityDto;

import java.util.List;

public interface MyPageDao {
    // 게시글 조회
    public List<CommunityDto> getUserMyContentAll(int userNo);
    // 댓글 조회
    public List<CommentDto> getComments(int userNo);
}

package com.multi.fourtunes.model.biz;

import com.multi.fourtunes.model.dto.CommentDto;
import com.multi.fourtunes.model.dto.CommunityDto;

import java.util.List;

public interface MyPageBiz {

    List<CommunityDto> getUserMyContentAll(int userNo);
    // 댓글
    List<CommentDto> getComments(int userNo);
}

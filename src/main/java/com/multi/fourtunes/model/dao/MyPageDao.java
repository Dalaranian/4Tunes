package com.multi.fourtunes.model.dao;

import com.multi.fourtunes.model.dto.CommunityDto;

import java.util.List;

public interface MyPageDao {
    public List<CommunityDto> getUserMyContentAll(int userNo);
}

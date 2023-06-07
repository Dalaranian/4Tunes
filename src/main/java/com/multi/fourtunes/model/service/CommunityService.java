package com.multi.fourtunes.model.service;

import java.util.List;

import com.multi.fourtunes.model.dto.CommunityDto;

public interface CommunityService {
    List<CommunityDto> getAll();
    CommunityDto get(int boardNo);
    void insert(CommunityDto community);
    void update(CommunityDto community);
    void delete(int boardNo);
}

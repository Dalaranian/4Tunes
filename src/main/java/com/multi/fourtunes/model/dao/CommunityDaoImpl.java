package com.multi.fourtunes.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.dto.CommentDto;
import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.dto.CommunityReportDto;
import com.multi.fourtunes.model.mapper.CommunityMapper;

@Repository
public class CommunityDaoImpl implements CommunityDao {

	@Autowired
	private CommunityMapper communityMapper;

	@Override
	public List<CommunityDto> getAll() {
		// List<CommunityDto> temp = communityMapper.getAll();
		// for (CommunityDto dto : temp) {
		// System.out.println(dto.toString());
		// }
		return communityMapper.getAll();
	}

	@Override
	public CommunityDto get(int boardNo) {
		return communityMapper.get(boardNo);
	}

	@Override
	public int insert(CommunityDto community) {
		return communityMapper.insert(community);
	}

	@Override
	public int update(CommunityDto community) {
		return communityMapper.update(community);
	}

	@Override
	public int delete(int boardNo) {
		return communityMapper.delete(boardNo);
	}

	@Override
	public int incrementViewCount(int boardNo) {
		return communityMapper.incrementViewCount(boardNo);
	}

	@Override
	public List<CommentDto> getComments(int boardNo) {
	    return communityMapper.getComments(boardNo);
	}
	
	@Override
	public int addComment(CommentDto comment) {
		return communityMapper.addComment(comment);
		
	}


	@Override
	public int deleteByBoardNo(int boardNo) {
		return communityMapper.deleteByBoardNo(boardNo);
	}

	@Override
	public CommentDto getComment(int commentNo) {
		return communityMapper.getComment(commentNo);
		
	}

	@Override
	public int deleteComment(int commentNo) {
		return communityMapper.deleteComment(commentNo);
		
	}

	@Override
	public int isReported(int userNo, int boardNo) {
		return communityMapper.isReported(userNo, boardNo);
	}

	@Override
	public int incrementReportCount(int boardNo) {
		return communityMapper.incrementReportCount(boardNo);
	}

	@Override
	public void reportCommunity(CommunityReportDto reportDto) {
		communityMapper.reportCommunity(reportDto);
		
	}

	
	
	

	
	

//    @Override
//    public void updateReportCount(int boardNo) {
//        communityMapper.updateReportCount(boardNo);
//    }
//
//    @Override
//    public int checkDuplicateReport(int boardNo, int userNo) {
//        return communityMapper.checkDuplicateReport(boardNo, userNo);
//    }
	
}

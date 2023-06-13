package com.multi.fourtunes.model.dao;

import java.util.List;

import com.multi.fourtunes.model.dto.CommentDto;
import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.dto.CommunityReportDto;

public interface CommunityDao {
	public List<CommunityDto> getAll();

	public CommunityDto get(int boardNo);

	int insert(CommunityDto community);

	int update(CommunityDto community);

	int delete(int boardNo);
	
	int incrementViewCount(int boardNo);

	List<CommentDto> getComments(int boardNo);
	
	int addComment(CommentDto comment);

	int deleteByBoardNo(int boardNo);

	public CommentDto getComment(int commentNo);

	int deleteComment(int commentNo);

	int isReported(int userNo, int boardNo);

	int incrementReportCount(int boardNo);

	public void reportCommunity(CommunityReportDto reportDto);


	


//	void updateReportCount(int boardNo);
//
//  public int checkDuplicateReport(int boardNo, int userNo);
}
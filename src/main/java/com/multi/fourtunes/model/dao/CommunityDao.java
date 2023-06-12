package com.multi.fourtunes.model.dao;

import java.util.List;

import com.multi.fourtunes.model.dto.CommentDto;
import com.multi.fourtunes.model.dto.CommunityDto;

public interface CommunityDao {
	public List<CommunityDto> getAll();

	public CommunityDto get(int boardNo);

	int insert(CommunityDto community);

	int update(CommunityDto community);

	int delete(int boardNo);
	
	int incrementViewCount(int boardNo);

	List<CommentDto> getComments(int boardNo);
	
	int addComment(CommentDto comment);

	int deleteComment(int boardNo, int userNo);


	


//	void updateReportCount(int boardNo);
//
//  public int checkDuplicateReport(int boardNo, int userNo);
}
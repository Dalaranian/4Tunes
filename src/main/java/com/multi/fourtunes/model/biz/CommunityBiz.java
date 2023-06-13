package com.multi.fourtunes.model.biz;

import java.util.List;

import com.multi.fourtunes.model.dto.CommentDto;
import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.dto.CommunityReportDto;

public interface CommunityBiz {
	List<CommunityDto> getAll();

	CommunityDto get(int boardNo);

	void insert(CommunityDto community);

	void update(CommunityDto community);

	void delete(int boardNo);

	void incrementViewCount(int boardNo);

	List<CommentDto> getComments(int boardNo);
	
	void addComment(CommentDto comment);

	void deleteByBoardNo(int boardNo);

	CommentDto getComment(int commentNo);

	void deleteComment(int commentNo);

	int isReported(int userNo, int boardNo);

	void incrementReportCount(int boardNo);

	void reportCommunity(CommunityReportDto reportDto);

	

	
	

//	void updateReportCount(int boardNo);
//	int checkDuplicateReport(int boardNo, int user_no);

}

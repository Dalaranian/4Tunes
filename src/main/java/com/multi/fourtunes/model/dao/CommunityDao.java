package com.multi.fourtunes.model.dao;

import java.util.List;

import com.multi.fourtunes.model.dto.CommentDto;
import com.multi.fourtunes.model.dto.CommentReportDto;
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

	public int isReported(int user_no, int boardNo);

	public void incrementReportCount(int boardNo);

	public void reportCommunity(CommunityReportDto reportDto);

	public int isCommentReported(int user_no, int commentNo);

	public void incrementCommentReportCount(int commentNo);

	public void reportComment(CommentReportDto reportDto);
	
	public List<CommunityDto> getUserMyContentAll(int userNo);
}
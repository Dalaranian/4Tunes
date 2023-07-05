package com.multi.fourtunes.model.biz;

import java.util.ArrayList;
import java.util.List;

import com.multi.fourtunes.model.dto.AdminCommentReportDto;
import com.multi.fourtunes.model.dto.AdminCommunityReportDto;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;

public interface AdminpageBiz {

	public List<UserDto> selectList();

	public String selectGrade(int user_no);

	public int updateGradePaid(int user_no);

	public int updateGradeFree(int user_no);

	public int deleteUser(int user_no);

	public List<UserDto> searchUser(String name);

	public List<AdminCommunityReportDto> selectReport();

	public int confirmReport(int board_no);

	public int deleteReport(int board_no);

	public List<AdminCommentReportDto> selectReportComment();

	public int confirmReportComment(int comment_no);

	public int deleteReportComment(int comment_no);

	public ArrayList<SongDto> setSonglink(ArrayList<SongDto> searchResult, String title);

	public String insertSong(String dto, String playlist);

}

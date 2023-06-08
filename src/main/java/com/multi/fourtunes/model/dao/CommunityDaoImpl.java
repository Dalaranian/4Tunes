package com.multi.fourtunes.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.multi.fourtunes.model.dto.CommunityDto;

@Repository
public class CommunityDaoImpl implements CommunityDao {
	private JdbcTemplate jdbcTemplate;

	/*
	 * private RowMapper<CommunityDto> rowMapper = new RowMapper<CommunityDto>() {
	 * 
	 * @Override public CommunityDto mapRow(ResultSet resultSet, int i) throws
	 * SQLException { CommunityDto community = new CommunityDto();
	 * community.setBoardNo(resultSet.getInt("BOARD_NO"));
	 * community.setTitle(resultSet.getString("TITLE"));
	 * community.setContent(resultSet.getString("CONTENT"));
	 * community.setUserNo(resultSet.getInt("USER_NO"));
	 * community.setReportCount(resultSet.getInt("REPORT_CNT"));
	 * community.setWriteDate(resultSet.getDate("WRITE_DATE"));
	 * community.setViewCount(resultSet.getInt("VIEW_CNT")); return community; } };
	 */

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<CommunityDto> getAll() {
		String query = "SELECT * FROM COMMUNITY_BOARD";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CommunityDto.class));
	}

	@Override
	public CommunityDto get(int boardNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(CommunityDto community) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(CommunityDto community) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int boardNo) {
		// TODO Auto-generated method stub
		return 0;
	}
}

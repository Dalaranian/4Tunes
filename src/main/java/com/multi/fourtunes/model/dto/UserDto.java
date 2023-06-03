package com.multi.fourtunes.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
	
	private int user_no;
	private String user_id;
	private String user_name;
	private String user_pw;
	private String user_grade;
	private int membership_duration;
	private Date pay_date;
}

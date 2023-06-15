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
public class PayDto {
	private int pay_no;
	private int user_no;
	private Date pay_date;
	private String pay_price;
}

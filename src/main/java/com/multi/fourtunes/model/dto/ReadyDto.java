package com.multi.fourtunes.model.dto;

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
public class ReadyDto {

	private String tid;
	private String next_redirect_pc_url;
	private String partner_order_id;
}

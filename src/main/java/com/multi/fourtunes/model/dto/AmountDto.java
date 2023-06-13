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
public class AmountDto {
	private int total;
	private int tax_free;
	private int vat;
	private int point;
	private int discount;
}

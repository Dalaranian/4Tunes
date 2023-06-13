package com.multi.fourtunes.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 결제 승인요청에 의하여 응답받는 정보
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApproveDto {
	private String aid;
	private String tid;
	private String cid;
	private String sid;
	private String partner_order_id;
	private String partner_user_id;
	private String payment_method_type;
	
	private AmountDto amount;
	
	private String item_name;
	private String item_code;
	private int quantity;
	private Date created_at;
	private Date approved_at;
	private String payload;
}

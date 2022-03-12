package com.andall.sally.supply.domain;

import com.andall.sally.supply.annotation.ExcelFields;
import lombok.Data;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 10:13 2021/12/31
 */
@Data
public class SensitiveDTO {
 
	@ExcelFields("id")
	private Long id;
	
	@ExcelFields("type")
	private String type;
	
	@ExcelFields("topic")
	private String topic;
	
	@ExcelFields("sensitiveWord")
	private String sensitiveWord;
	
}

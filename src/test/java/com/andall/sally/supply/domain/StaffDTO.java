package com.andall.sally.supply.domain;

import com.andall.sally.supply.annotation.ExcelFields;
import lombok.Data;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 19:22 2022/1/17
 */
@Data
public class StaffDTO {
	
	@ExcelFields("id")
	private Long id;
	
	@ExcelFields("name")
	private String name;
	
	@ExcelFields("mobile")
	private String mobile;
	
}

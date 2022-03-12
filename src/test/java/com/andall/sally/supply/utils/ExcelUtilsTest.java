package com.andall.sally.supply.utils;

import com.andall.sally.supply.domain.SensitiveDTO;
import com.andall.sally.supply.domain.StaffDTO;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 10:12 2021/12/31
 */
public class ExcelUtilsTest {
	
	@Test
	public void readExcelTest() throws Exception {
		File file = new File("/Users/lsl/Downloads/好特卖2022年在职名单.xlsx");
		
		InputStream inputStream = new FileInputStream(file);
		
		List<StaffDTO> sensitiveDTOS = ExcelUtils.readInputStreamData(inputStream, StaffDTO.class);
		// INSERT INTO `htm_staff` (`id`, `mobile`, `name`, `status`, `create_date`, `update_date`) VALUES
		//('8', '15372312788', '桑香香', '1', '2022-01-13 13:46:47', '2022-01-11 16:29:01');
		for (StaffDTO staffDTO : sensitiveDTOS) {
			if (staffDTO.getMobile().trim().length() != 11) {
				System.out.println(staffDTO.getName());
				break;
			}
			System.out.println("(" + staffDTO.getId() + ",'" + staffDTO.getName() + "','" + staffDTO.getMobile().trim() + "'),");
		}
	}
	
}

package com.andall.sally.supply.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 18:17 2020/3/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchReq {
    private int pageNum;
    private int pageSize;
}

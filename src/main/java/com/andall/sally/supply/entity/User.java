package com.andall.sally.supply.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 10:45 上午 2020/2/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userName;
    private String passWord;
}

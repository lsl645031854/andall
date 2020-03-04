package com.andall.sally.supply.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 5:40 下午 2020/3/4
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileModel {

    private String fileName;

    private byte[] content;

}

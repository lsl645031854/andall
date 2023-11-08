package com.andall.sally.supply.designpattern.factory;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 11:28 2020/3/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Pear extends Fruit {
    private int typeCount;
}

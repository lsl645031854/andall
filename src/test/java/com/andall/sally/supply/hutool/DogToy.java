package com.andall.sally.supply.hutool;

import cn.hutool.core.clone.CloneSupport;

import java.io.Serializable;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 16:28 2020/7/15
 */
public class DogToy extends CloneSupport<DogToy> implements Serializable {

    private static final long serialVersionUID = -7596520151083430780L;
    private String toyName;

    public DogToy() {
    }

    public DogToy(String toyName) {
        this.toyName = toyName;
    }

    public String getToyName() {
        return toyName;
    }

    public void setToyName(String toyName) {
        this.toyName = toyName;
    }

    @Override
    public String toString() {
        return "DogToy{" + "toyName='" + toyName + '\'' + '}';
    }
}

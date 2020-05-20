package com.andall.sally.supply.hutool;

import cn.hutool.core.clone.CloneSupport;

import java.io.Serializable;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 16:12 2020/7/15
 */
public class Dog extends CloneSupport<Dog> implements Serializable {
    private static final long serialVersionUID = -5977300664642266445L;
    private String name;
    private Integer age;

    private DogToy dogToy;

    public Dog(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public DogToy getDogToy() {
        return dogToy;
    }

    public void setDogToy(DogToy dogToy) {
        this.dogToy = dogToy;
    }
}

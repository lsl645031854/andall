package com.andall.sally.supply.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: lsl
 * @Description:  sdad
 * @Date: Created on 10:45 上午 2020/2/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 7994449903768935620L;
    private String userName;
    private String passWord;
    private Integer age;
    private boolean member;

    public User(Integer age, boolean member) {
        this.age = age;
        this.member = member;
    }

    public User(String userName, String passWord, Integer age) {
        this.userName = userName;
        this.passWord = passWord;
        this.age = age;
    }

    public String sayWhat(String word) {
        return this.userName + " say: " + word;
    }
}

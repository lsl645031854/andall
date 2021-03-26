package com.andall.sally.supply.entity;

import com.andall.sally.supply.annotation.FieldNumberAnnotation;
import com.andall.sally.supply.annotation.SyncAnnotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lsl
 * @Description:  sdad
 * @Date: Created on 10:45 上午 2020/2/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNumberAnnotation(fieldNum = 5)
public class User implements Serializable {
    private static final long serialVersionUID = 7994449903768935620L;

    @SyncAnnotation(sort = 0, type = String.class)
    private String userName;
    @SyncAnnotation(sort = 1, type = Integer.class)
    private Integer age;
    @SyncAnnotation(sort = 2, type = Date.class)
    private Date birthday;
    @SyncAnnotation(sort = 3, type = Boolean.class)
    private boolean member;
    @SyncAnnotation(sort = 4, type = BigDecimal.class)
    private BigDecimal price;
    @SyncAnnotation(sort = 5, type = String.class, valid = false)
    private String passWord;

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

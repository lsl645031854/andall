package com.andall.sally.supply.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 09:52 2020/9/23
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String name;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            Person person = (Person) obj;
            return name.equalsIgnoreCase(person.getName().trim());
        }
        return false;
    }
}

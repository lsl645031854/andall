package com.andall.sally.supply.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 09:55 2020/9/23
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubPerson extends Person {
    private Integer id;

    public SubPerson(Integer id, String name) {
        super(name);
        this.id = id;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if(obj instanceof SubPerson){
//            SubPerson e = (SubPerson)obj;
//            return super.equals(obj) && e.getId().equals(id);
//        }
//        return super.equals(obj);
//    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Person){
            Person e = (Person)obj;
            return super.equals(e);
        }

        return super.equals(obj);
    }

    public static void main(String[] args) {
        Person p1 = new Person("tom");
        SubPerson p2 = new SubPerson(1, "tom");
        SubPerson p3 = new SubPerson(2, "tom");

        System.out.println(p1.equals(p1)); // true 自反性

        System.out.println(p1.equals(p2)); // true 对称型
        System.out.println(p2.equals(p1)); // true

        System.out.println(p1.equals(p3)); // true // 传递性
        System.out.println(p3.equals(p2)); // true
    }
}

package com.andall.sally.supply.sensitive;

import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.Set;

/**
 * @Author: lsl
 * @Description: DFA 敏感词过滤
 * @Date: Created on 15:00 2020/7/7
 */
public class SensitiveWordTest {


    public static void main(String[] args) throws Exception {

        SensitiveWordInit sensitiveWordInit = new SensitiveWordInit();
        Map<String, Object> sensitiveWordMap = sensitiveWordInit.initKeyWord();
        String s = JSON.toJSONString(sensitiveWordMap);
        System.out.println(s);

        SensitivewordEngine.sensitiveWordMap = sensitiveWordMap;
//        Set<String> sensitiveWord = SensitivewordEngine.getSensitiveWord("太王四神不是泰兴幼指中国人", 1);
//        System.out.println(sensitiveWord);
    
        boolean containSensitiveWord = SensitivewordEngine.isContainSensitiveWord("谁是十八大接班人", 2);
        System.out.println(containSensitiveWord);
    
        String replaceSensitiveWord = SensitivewordEngine.replaceSensitiveWord("太王四神不是指中国人", 2, "*");
        System.out.println(replaceSensitiveWord);
    }

}


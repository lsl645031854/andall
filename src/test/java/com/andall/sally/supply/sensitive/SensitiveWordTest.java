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
        Map sensitiveWordMap = sensitiveWordInit.initKeyWord();
        System.out.println(JSON.toJSONString(sensitiveWordMap));

        SensitivewordEngine.sensitiveWordMap = sensitiveWordMap;
        Set<String> sensitiveWord = SensitivewordEngine.getSensitiveWord("中亚人不是指中国人", 2);
        System.out.println(sensitiveWord);

        String replaceSensitiveWord = SensitivewordEngine.replaceSensitiveWord("中亚人不是指中国人", 2, "*");
        System.out.println(replaceSensitiveWord);
    }

}


package com.andall.sally.supply.sensitive;

import org.junit.platform.commons.util.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 敏感词库初始化 在实现文字过滤的算法中，DFA是唯一比较好的实现算法
 *
 * @author lsl
 */
public class SensitiveWordInit {
    /**
     * 敏感词库
     */
    private static Map<String, Object> sensitiveWordMap;

    /**
     * 初始化敏感词
     *
     * @return
     */
    public Map<String, Object> initKeyWord() {
        try {
            // 从敏感词集合对象中取出敏感词并封装到Set集合中
            Resource res = new ClassPathResource("/sensitiveWord.txt");
            File file = res.getFile();
            InputStream is = new FileInputStream(file);

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String str;
            Set<String> keyWordSet = new HashSet<>();
            do {
                str = reader.readLine();
                if (StringUtils.isNotBlank(str)) {
                    keyWordSet.add(str);
                }
            } while (StringUtils.isNotBlank(str));
            // 将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }

    /**
     * 封装敏感词库
     *
     * @param keyWordSet keyWordSet
     */
    @SuppressWarnings("all")
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        // 初始化HashMap对象并控制容器的大小
        sensitiveWordMap = new HashMap<>(keyWordSet.size());
        // 用来辅助构建敏感词库
        Map<String, Object> newWorMap;
        // 用来按照相应的格式保存敏感词库数据
        Map<String, Object> nowMap;
        // 使用一个迭代器来循环敏感词集合
        for (String keyWord : keyWordSet) {
            // 等于敏感词库，HashMap对象在内存中占用的是同一个地址，所以此nowMap对象的变化，sensitiveWordMap对象也会跟着改变
            nowMap = sensitiveWordMap;
            for (int i = 0; i < keyWord.length(); i++) {
                // 截取敏感词当中的字，在敏感词库中字为HashMap对象的Key键值
                String key = String.valueOf(keyWord.charAt(i));
                // 判断这个字是否存在于敏感词库中
                Object wordMap = nowMap.get(key);
                if (wordMap != null) {
                    nowMap = (Map) wordMap;
                } else {
                    newWorMap = new HashMap<>();
                    newWorMap.put("isEnd", "0");
                    nowMap.put(key, newWorMap);
                    nowMap = newWorMap;
                }
                // 如果该字是当前敏感词的最后一个字，则标识为结尾字
                if (i == keyWord.length() - 1) {
                    nowMap.put("isEnd", "1");
                }
            }
        }
    }
}

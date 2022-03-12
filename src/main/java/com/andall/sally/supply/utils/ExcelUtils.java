package com.andall.sally.supply.utils;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;
import com.andall.sally.supply.annotation.ExcelFields;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xubangbang
 * @date 2021/7/20 13:23
 */
public class ExcelUtils {


    /**
     * 将实体数据写入至Excel输出流
     *
     * @param data 数据
     * @param <T>
     * @return
     */
    public static <T> ByteArrayOutputStream exportDataOutputStream(List<T> data) {
        ExcelWriter writer = new ExcelWriter();
        writer.writeRow(getTitlesByModel(data.get(0).getClass())).write(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writer.flush(outputStream).close();
        return outputStream;
    }

    /**
     * 将实体数据写入至Excel输出流
     *
     * @param data 数据
     * @param <T>
     * @return
     */
    public static <T> ByteArrayOutputStream exportBaseDataOutputStream(List<T> data) {
        ExcelWriter writer = new ExcelWriter();
        writer.write(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writer.flush(outputStream).close();
        return outputStream;
    }

    /**
     * 获取实体类属性上 ExcelFields注解的标题值，如果没有 就去属性名
     *
     * @param model 实体类型
     * @return 标题集合
     * @see ExcelFields excel标题注解
     */
    private static List<String> getTitlesByModel(Class<?> model) {
        Object t1 = ReflectUtil.newInstance(model);
        Field[] declaredFields = t1.getClass().getDeclaredFields();
        return Stream.of(declaredFields).map(filed -> {
            ExcelFields annotation = filed.getAnnotation(ExcelFields.class);
            String result = filed.getName();
            if (annotation != null && StringUtils.isNotBlank(annotation.value())) {
                result = annotation.value();
            }
            return result;
        }).collect(Collectors.toList());
    }


    /**
     * 读取数据文件为指定对象
     *
     * @param inputStream excel文档
     * @param clazz       指定的class对象 ，对应的对象字段要有 {@link ExcelFields#value()} 标题值需要与excel文档第一行标题一致
     * @param <T>         泛型对象
     * @return 返回集合泛型对象
     */
    public static <T> List<T> readInputStreamData(InputStream inputStream, Class<T> clazz) {
        List<Map<String, Object>> maps = readInputStreamData(inputStream);
        return maps.stream().map(map -> {
            T t = ReflectUtil.newInstance(clazz);
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field filed : declaredFields) {
                ExcelFields annotation = filed.getAnnotation(ExcelFields.class);
                if (annotation == null) {
                    continue;
                }
                String titleValue = annotation.value();
                for (String k : map.keySet()) {
                    if (titleValue.equals(k)) {
                        filed.setAccessible(true);
                        if (map.get(k) != null) {
                            ReflectUtil.setFieldValue(t, filed, map.get(k).toString());
                        }
                        break;
                    }
                }
            }
            return t;
        }).collect(Collectors.toList());
    }

    public static List<Map<String, Object>> readInputStreamData(InputStream inputStream) {
        ExcelReader reader = new ExcelReader(inputStream, 0, true);
        return reader.readAll();
    }

}

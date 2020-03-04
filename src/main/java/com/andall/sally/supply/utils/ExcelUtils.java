package com.andall.sally.supply.utils;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.andall.sally.supply.model.FileModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 5:38 下午 2020/3/4
 */
@Component
@Slf4j
public class ExcelUtils {

    public <T extends BaseRowModel> List<T> parseExcel(FileModel request, Class<T> clazz) {
        List<T> propertyList = new ArrayList<>();
        if (request.getContent().length == 0) {
            log.info("文件为空");
            return null;
        }
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(request.getContent());
            ExcelReader reader = new ExcelReader(inputStream, null,
                    new AnalysisEventListener<T>() {
                        @Override
                        public void invoke(T object, AnalysisContext context) {
                            log.info("当前sheet:" + context.getCurrentSheet().getSheetNo() + " 当前行：" + context.getCurrentRowNum()
                                    + " data:" + object);
                            if (!isAllFieldNull(object)) {
                                propertyList.add(object);
                            }
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                        }

                    });
            reader.read(new Sheet(1, 1, clazz));
        } catch (Exception e) {
            log.info("解析数据报错:{}", e.getLocalizedMessage());
            throw new RuntimeException();
        } finally {
            try {
                assert inputStream != null;
                inputStream.close();
            } catch (IOException e) {
                log.info("解析数据报错:{}", e.getLocalizedMessage());
            }
        }
        return propertyList;
    }


    public  boolean isAllFieldNull(Object obj) {
        boolean flag = true;
        try {
            // 得到类对象
            Class stuCla = (Class) obj.getClass();
            //得到属性集合
            Field[] fs = stuCla.getDeclaredFields();
            //遍历属性
            for (Field f : fs) {
                // 设置属性是可以访问的(私有的也可以)
                f.setAccessible(true);
                // 得到此属性的值
                Object val = null;

                val = f.get(obj);
                //只要有1个属性不为空,那么就不是所有的属性值都为空
                if (val != null) {
                    flag = false;
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            log.error("判断对象属性是否全部为空方法出错", e);
        }

        return flag;
    }
}

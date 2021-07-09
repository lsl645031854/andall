package com.andall.sally.supply.youren;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.andall.sally.supply.annotation.FieldNumberAnnotation;
import com.andall.sally.supply.annotation.SyncAnnotation;
import com.andall.sally.supply.entity.User;
import com.andall.sally.supply.utils.HttpConnectionUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jpedal.parser.shape.S;
import org.junit.Test;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 10:06 上午 2021/3/9
 */
public class promotionTest {

    @Test
    public void test2() {
        // 读取txt
        try {
            InputStreamReader read  = new InputStreamReader(new FileInputStream(new File("/Users/shuailingli/Desktop/游仁/PROM_20210309050000017/PROMOTIONALLOCATE_20210309050000017.txt")), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            int i = 0;
            while((lineTxt = bufferedReader.readLine()) != null){
                if (i == 3) {
                    break;
                }
                i = i + 1;
                String[] fileDates = lineTxt.split("\t", -1);

                System.out.println("第" + i + "行 ---> " + lineTxt);

                System.out.println(fileDates.length + "-->" +  Arrays.asList(fileDates));
            }
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    public void test() {
        try {
//            getURLResource("/Users/shuailingli/Desktop/游仁/测试.zip", "https://cdn.chinalawson.com.cn/d051cfaa2fa34c18cc66131e3ade6762/6046dad6/YOREN/CSHOP/PROMOTION/PROM_20210309050000017.zip");
            String s = downloadFromUrl("https://cdn.chinalawson.com.cn/5d3bf2345d2691322e95421eb46dec1d/6046fdca/YOREN/CSHOP/PROMOTION/PROM_20210309050000017.zip");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() {
        try {
            HttpConnectionUtil.downloadFile("https://cdn.chinalawson.com.cn/8f30bc28def8ec58f86c87d556e73bca/60475914/YOREN/CSHOP/PROMOTION/PROM_20210309050000017.zip", "/Users/shuailingli/Desktop/游仁");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public String downloadFromUrl(String url) {

        try {
            URL httpurl = new URL(url);
            File f = new File("/Users/shuailingli/Desktop/游仁/测试.zip");
            FileUtils.copyURLToFile(httpurl, f);
        } catch (Exception e) {
            e.printStackTrace();
            return "Fault!";
        }
        return "Successful!";
    }

    public void getURLResource(String ourPutFile,String urlStr) throws Exception {

        FileWriter fw = new FileWriter(ourPutFile);

        PrintWriter pw = new PrintWriter(fw);

        URL resourceUrl = new URL(urlStr);

        InputStream content = (InputStream) resourceUrl.getContent();

        BufferedReader in = new BufferedReader(new InputStreamReader(content));

        String line;

        while ((line = in.readLine()) != null) {
            pw.println(line);
        }
        pw.close();
        fw.close();
    }

    @Test
    public void testAnnotation() throws Exception {
        String[] arr = {"root",  "18", "2020-12-21 12:34:23", "true", "12.50"};

        User user = new User();

        if (user.getClass().isAnnotationPresent(FieldNumberAnnotation.class)) {
            FieldNumberAnnotation numberAnnotation = user.getClass().getAnnotation(FieldNumberAnnotation.class);
            int fieldNum = numberAnnotation.fieldNum();

            Field[] fields = user.getClass().getDeclaredFields();

            if (fields.length < fieldNum) {
                System.out.println("数组长度不能小于有效字段数....");
                return;
            }

            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(SyncAnnotation.class)) {
                    SyncAnnotation annotation = field.getAnnotation(SyncAnnotation.class);
                    boolean valid = annotation.valid();
                    if (!valid) {
                        continue;
                    }
                    int sort = annotation.sort();
                    Class<?> type = annotation.type();
                    String value = arr[sort];
                    if (String.class.isAssignableFrom(type)) {
                        field.set(user, value);
                    } else if (Date.class.isAssignableFrom(type)) {
                        if (StringUtils.isBlank(value)) {
                            field.set(user, null);
                        } else {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = simpleDateFormat.parse(value);
                            field.set(user, date);
                        }
                    } else if (Integer.class.isAssignableFrom(type)) {
                        field.set(user, StringUtils.isBlank(value) ? null : Integer.parseInt(value));
                    } else if (Boolean.class.isAssignableFrom(type)) {
                        field.set(user, StringUtils.isBlank(value) ? null : Boolean.valueOf(value));
                    } else if (BigDecimal.class.isAssignableFrom(type)) {
                        field.set(user, StringUtils.isBlank(value) ? null : new BigDecimal(value));
                    }

                }
            }

            System.out.println(user);
        }

    }

    @Test
    public void testClass() throws ParseException {
        System.out.println(BigDecimal.class.isAssignableFrom(Long.class));
        File file = new File("/Users/shuailingli/Desktop/游仁/promotion/l");
        File[] files = file.listFiles();
        System.out.println(files.length);
        for (File file1 : files) {
            System.out.println(file1.getAbsolutePath());
        }
//        String s = StringUtils.substringBeforeLast("/Users/shuailingli/Desktop/游仁/PROM_20210309050000017.zip", ".");
//        System.out.println(s);
//        String s1 = FileNameUtil.mainName("/Users/shuailingli/Desktop/游仁/PROM_20210309050000017.zip");
//        System.out.println(s1);
//        String fileTimeStr = FileTypeUtil.getType(new File("/Users/shuailingli/Desktop/游仁/promotion/PROM_20210310050000012.zip.20210310.ok"));
//        System.out.println(fileTimeStr);

    }

    @Test
    public void testCal() {

        GregorianCalendar now = new GregorianCalendar();
        //一周第一天是否为星期天
        boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
        System.out.println(isFirstSunday);
        //获取周几
        int weekDay = now.get(Calendar.DAY_OF_WEEK);
        //若一周第一天为星期天，则-1
        if(isFirstSunday){
            weekDay = weekDay - 1;
            if(weekDay == 0){
                weekDay = 7;
            }
        }
        //打印周几
        System.out.println(weekDay);
    }

    @Test
    public void testCast() {
            Set<String> set = new HashSet<>();
            set.add("202103090012");
            set.add("201809180006");
            set.add("202103090023");
            set.add("202103090010");

        Optional<String> max = set.stream().max(Comparator.naturalOrder());
        System.out.println(max.orElse(""));
    }

    @Test
    public void testLong() throws Exception {
        Set<String> strings = new HashSet<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.add("6");

        Set<String> strings1 = new HashSet<>();
        strings1.add("2");
        strings1.add("3");
        strings1.add("5");

        Set<String> strings2 = new HashSet<>();
        strings2.add("1");
        strings2.add("2");
        strings2.add("3");
        strings2.add("7");

        strings1.retainAll(strings);
        strings2.retainAll(strings);
        System.out.println(strings1);
        System.out.println(strings2);
        System.out.println(strings);
    }

    @Test
    public void testAnnotation1() throws Exception {
        User user = new User();
        user.setAge(12);
        user.setBirthday(new Date());
        user.setUserName("tom");

        Class<? extends User> aClass = user.getClass();

        Field field = aClass.getDeclaredField("age");
        field.setAccessible(true);
        Object o = field.get(user);
        System.out.println(o);

        PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), aClass);
        Method readMethod = descriptor.getReadMethod();
        Object invoke = readMethod.invoke(user);
        System.out.println(invoke);

//        Method getUserName = aClass.getDeclaredMethod("getUserName");
//        Object invoke = getUserName.invoke(user);
//        System.out.println(invoke);
//        System.out.println(invoke.getClass().isAssignableFrom(String.class));
    }

}

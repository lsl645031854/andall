package com.andall.sally.supply.youren;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.andall.sally.supply.entity.PointTemporaryEntity;
import com.andall.sally.supply.entity.UserGroupEntity;
import com.andall.sally.supply.mapper.PointTemporaryEntityMapper;
import com.andall.sally.supply.mapper.UserGroupEntityMapper;
import com.google.common.collect.Maps;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jpedal.parser.shape.D;
import org.jpedal.parser.shape.S;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class PointTest {

    public static AtomicBoolean execFlag = new AtomicBoolean(true);

    @Autowired
    private PointTemporaryEntityMapper temporaryEntityMapper;

    @Autowired
    private UserGroupEntityMapper userGroupMapper;

    @Test
    public void testBoolean() {
        boolean b = execFlag.compareAndSet(true, false);
        System.out.println(b);
        System.out.println(execFlag.get());
        System.out.println("================");
        boolean b1 = execFlag.compareAndSet(true, false);
        System.out.println(b1);
        System.out.println(execFlag.get());
    }

    @Test
    public void testSelect() {

        String region = "sh-lawson";
        String prefix = StringUtils.substringBefore(region, "-");
        String tableCommon = "z_ph_%s";
        String table = String.format(tableCommon, prefix);

        List<PointTemporaryEntity> pointTemporaryEntities = temporaryEntityMapper.selectByRegion(table, region, 0, 4);

        for (PointTemporaryEntity pointTemporaryEntity : pointTemporaryEntities) {
            System.out.println(pointTemporaryEntity);
        }
    }

    @Test
    public void testCreateUrl() {
        String region = "sh-lawson";
        int total = 2434557;
        int pageSize = 50000;
        String url = "https://lawsonadmin.yorentown.com/admin/v1/cron/pointClear";

        int remainder = total % pageSize;
        int urlNum;
        if (remainder != 0) {
            urlNum = (total / pageSize) + 1;
        } else {
            urlNum = total / pageSize;
        }


        for (int i = 1; i <= urlNum; i++) {
            Map<String,Object> param = Maps.newHashMap();
            param.put("regionBlockCode", region);
            param.put("maxId", (i-1) * pageSize + pageSize);
            param.put("offset", (i-1) * pageSize);
            param.put("uuid", "b51861dc8a3c4c59b21f7b4b595cddbd");

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            param.forEach(builder::queryParam);
            String url2 = builder.build().encode().toString();
            System.out.println(url2);
        }
    }

    @Test
    public void testUUID() {
        String s = "罗森203265泰兴路230号店";

        String s2 = EmojiParser.parseToAliases(s);
        System.out.println(s2);
        System.out.println(EmojiParser.parseToAliases(s, EmojiParser.FitzpatrickAction.PARSE));
        System.out.println(EmojiParser.parseToAliases(s, EmojiParser.FitzpatrickAction.REMOVE));
        System.out.println(EmojiParser.parseToAliases(s, EmojiParser.FitzpatrickAction.IGNORE));
        String s1 = EmojiParser.parseToUnicode(s);
        System.out.println(s1);
    }

    public static String filterEmoji(String source) {
        if(source != null)
        {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]|" +
                            "[\ud83e\udd00-\ud83e\uddff]|[\u2300-\u23ff]|[\u2500-\u25ff]|[\u2100-\u21ff]|[\u00a0-\u0fff]|[\u2b00-\u2bff]|[\u2d06]|[\u3030]"
                    ,Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE ) ;

            Matcher emojiMatcher = emoji.matcher(source);
            if ( emojiMatcher.find())
            {
                source = emojiMatcher.replaceAll("*");
                return source ;
            }
            return source;
        }
        return source;
    }

    @Test
    public void generateTemporaryDada() {
        List<UserGroupEntity> userGroupEntities = userGroupMapper.selectAll();

        for (UserGroupEntity userGroupEntity : userGroupEntities) {
            PointTemporaryEntity temporaryEntity = new PointTemporaryEntity();
            temporaryEntity.setUserId(userGroupEntity.getUserId());
            temporaryEntity.setGroupId(userGroupEntity.getGroupId());
            temporaryEntity.setClearingPoint(1);

            temporaryEntityMapper.insertSelective(temporaryEntity);
        }
    }


    public static void main(String[] args) {
        Date date = new Date(1613801542*1000L);
        System.out.println(date);
        System.out.println(System.currentTimeMillis());
    }

    public static boolean isBlankOr(Object... objects) {

        int length = objects.length;
        for (int i = 0; i < length; i++) {
            Object obj = objects[i];
            if (isBlankObj(obj))
                return true;
        }
        return false;
    }

    public static boolean isBlankObj(Object obj) {
        if (null == obj) {
            return true;
        }
        if (obj instanceof String) {
            if (isBlank(obj.toString()) || "null".equals(obj.toString()))
                return true;
        }
        if (obj instanceof List) {
            List ls = (List) obj;
            if (ls.isEmpty())
                return true;
        }
        if (obj instanceof Map) {
            Map m = (Map) obj;
            if (m.isEmpty())
                return true;
        }
        if (obj instanceof Set) {
            Set s = (Set) obj;
            if (s.isEmpty())
                return true;
        }
        if (obj instanceof Object[]) {
            Object[] array = (Object[]) obj;
            if (array.length == 0)
                return true;
        }

        return false;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    @Test
    public void testCollection() {
        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");

        List<String> list1 = new ArrayList<>();
        list1.add("2");
        list1.add("3");
        list1.add("4");

        Collection disjunction = CollectionUtils.subtract(list, list1);
        System.out.println(disjunction);
    }

    @Test
    public void testBigDecimal() {
        Calendar acceptTimeOut = Calendar.getInstance();
        //for dev
//            acceptTimeOut.add(Calendar.MINUTE, -10);
        acceptTimeOut.add(Calendar.HOUR_OF_DAY, -1);
        System.out.println(acceptTimeOut.getTime());

        String orderNo = "11521042849100300001";
        int length = orderNo.length();

        System.out.println(orderNo.substring(length - 16));

        System.out.println(null + "00000");

        System.out.println(new BigDecimal("0.65"));
    }

    @Test
    public void testIf() {
        List<String> list = new ArrayList<>();
        String s = list.get(0);
        System.out.println(s);
    }

    @Test
    public void test5() {
        String content = "010000";
//        char[] chars = content.toCharArray();
//        chars[2] = '1';
//        String s = String.valueOf(chars);
//        System.out.println(s);

        Pattern pattern = Pattern.compile("[01]");
        Matcher matcher = pattern.matcher(content);
        List<String> list = new ArrayList<>();
        while(matcher.find()){
            list.add(matcher.group());
        }
        list.set(2, "1");
        String collect = String.join("", list);
        System.out.println(collect);
    }

    @Test
    public void testDate() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(new Date(1685513833L)));

        long time1 = new Date().getTime();
        System.out.println(time1);
        TimeUnit.SECONDS.sleep(3);
        long time2 = new Date().getTime();
        System.out.println(time2);

        long between = DateUtil.between(new Date(time2), new Date(time1), DateUnit.SECOND);
        System.out.println(between);
    }

    @Test
    public void testJson() {
        String params = "timestamp=1627901047300&nonce=aaaaaa&signature=da3f0af30de1bac68ad377d2a2a0c3bb21ad1ad0&a=123";

        System.out.println(JSON.toJSONString(params));

        String[] split = params.split("&");
        List<String> excludeParams = new ArrayList<>();
        excludeParams.add("signature");
        excludeParams.add("nonce");
        excludeParams.add("timestamp");

        String collect =
                Stream.of(split).filter(param -> !param.contains("signature")).sorted().collect(Collectors.joining("&"));
        System.out.println(collect);
    }

    @Test
    public void testS() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        TimeUnit.SECONDS.sleep(2);
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        System.out.println(totalTimeMillis);
    }

    @Test
    public void testBig() {
        BigDecimal b1 = new BigDecimal(10);
        BigDecimal b2 = new BigDecimal(8);
        System.out.println(b1.compareTo(b2) <= 0);

        System.out.println(DateUtils.addDays(new Date(), 1));
    }

    @Test
    public void testInteger() {
//        Integer integer = Integer.parseInt("1.6");
        int integer = Double.valueOf("1.667").intValue();
        System.out.println(integer);
    }

    @Test
    public void testInteger1() {
        double integer = Double.parseDouble("1.000");
        System.out.println(integer);
        System.out.println((int)integer);
        System.out.println(integer > (int)integer );
    }

    @Test
    public void testStr() {
    	String build = new StringBuilder().append("阿里").append("巴巴").toString();
    	String build1 = new StringBuilder().append("a").append("b").toString();
    	String build2 = "ab";
	    System.out.println(build == build1);
	    System.out.println(build == build2);
	    System.out.println(build == build.intern());

	    String s1 = "hello" + "word";
	    String s2 = "helloword";
	    System.out.println(s1 == s2);

	    String str1 = new StringBuilder("计算机").append("软件").toString();
	    System.out.println(str1.intern() == str1);

	    String str2 = new StringBuilder("ja").append("va").toString();
	    System.out.println(str2.intern() == str2);

	    String string = new String("ab") + new String("c");
	    String string1 = "abc";
	    System.out.println(string.intern() == string1);
    }

    @Test
    public void testDateFormat() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = format.parse("2021-08-27 17:31:32");
        String s = dateFormat(new Date(), parse, DateUnit.SECOND);
        System.out.println(s);
    }

    private String dateFormat(Date now, Date date, DateUnit unit) {

        long var1 = DateUtil.between(date, now,unit);
        switch (unit) {
            case SECOND:
                if (var1 > 60) {
                    return dateFormat(date, now, DateUnit.MINUTE);
                } else {
                    return var1 + "秒前";
                }
            case MINUTE:
                if (var1 > 60) {
                    return dateFormat(date, now, DateUnit.HOUR);
                } else {
                    return var1 + "分钟前";
                }
            case HOUR:
                if (var1 > 24) {
                    return "1天前";
                } else {
                    return "1小时前";
                }
            default:
        }
        return "";
    }
}

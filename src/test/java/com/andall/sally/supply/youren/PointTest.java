package com.andall.sally.supply.youren;

import com.andall.sally.supply.entity.PointTemporaryEntity;
import com.andall.sally.supply.entity.UserGroupEntity;
import com.andall.sally.supply.mapper.PointTemporaryEntityMapper;
import com.andall.sally.supply.mapper.UserGroupEntityMapper;
import com.google.common.collect.Maps;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

}

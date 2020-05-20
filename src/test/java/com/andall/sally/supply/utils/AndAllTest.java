package com.andall.sally.supply.utils;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 13:12 2020/9/23
 */
public class AndAllTest {

    @Test
    public void test1() throws ParseException {
        /**
         INSERT INTO `shop_activ`.`activ_grow_base`(`id`, `user_id`, `link_man_id`, `gestational_week`, `gestational_day`, `gestational_age`, `pregnancy_time`, `delete_flag`)
         VALUES
         (3227421897179136, 3227194352074752, 3227194801162240, 39, 1, 274, '2019-11-11 00:00:00', 1);


         INSERT INTO `shop_activ`.`activ_grow_record`(`id`, `user_id`, `link_man_id`, `detection_time`, `height`, `head_circle`, `weight`, `percentage`, `delete_flag`)
         VALUES
         (3227422031986688, 3227194352074752, 3227194801162240, '2020-08-11 00:00:00', 49.00, NULL, 3, NULL, 1);

         INSERT INTO `shop_activ`.`activ_grow_record`(`id`, `user_id`, `link_man_id`, `detection_time`, `height`, `head_circle`, `weight`, `percentage`, `delete_flag`)
         VALUES
         (3227422179655680, 3227194352074752, 3227194801162240, '2020-09-22 00:00:00', 57, 39, 5.17, NULL, 1);
         */
        int gestationalAge = 39 * 7 + 1;

        Date birthday = DateUtils.parseDate("2020-08-11", "yyyy-MM-dd");
        // 妊娠时间
        Date pregnancyTime = DateUtils.addDays(birthday, -gestationalAge);

        System.out.println("胎龄：" + gestationalAge);
        System.out.println("妊娠时间 pregnancyTime ：" + DateUtil.format(pregnancyTime, "yyyy-MM-dd"));
    }

    @Test
    public void test2() throws ParseException {
        Date birthday = DateUtils.parseDate("2020-08-11", "yyyy-MM-dd");
        // 检测时间
        Date detectionTime = DateUtils.addDays(birthday, 42);
        System.out.println("检测时间 detectionTime ：" + DateUtil.format(detectionTime, "yyyy-MM-dd"));

    }

    @Test
    public void test3() {

    }
}

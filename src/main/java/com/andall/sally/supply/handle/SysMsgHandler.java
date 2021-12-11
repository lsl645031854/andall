package com.andall.sally.supply.handle;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 21:09 2020/11/19
 */

import com.andall.sally.supply.annotation.MsgTypeEnum;
import com.andall.sally.supply.annotation.TypeAnnotation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@TypeAnnotation(value = MsgTypeEnum.SYS_MSG)
public class SysMsgHandler  extends BaseHandler {
    @Override
    @Transactional
    public void handleMsg(String content) {
        System.out.println("This is sysMsg. Detail msg information is :" + content);
    }
}

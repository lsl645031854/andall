package com.andall.sally.supply.handle;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 21:09 2020/11/19
 */

import com.andall.sally.supply.annotation.MsgTypeEnum;
import com.andall.sally.supply.annotation.TypeAnnotation;
import org.springframework.stereotype.Component;

@Component
@TypeAnnotation(value = MsgTypeEnum.CHAT_MSG)
public class ChatMsgHandler implements BaseMsgHandler {
    @Override
    public void handleMsg(String content) {
        System.out.println("This is chatMsg. Detail msg information is :" + content);
    }
}

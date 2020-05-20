package com.andall.sally.supply.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 21:05 2020/11/19
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MsgTypeEnum {

    // 聊天信息
    CHAT_MSG("chatMsg", "聊天信息"),

    // 系统信息
    SYS_MSG("sysMsg", "系统信息");

    private String code;

    private String zhDesc;


    public static MsgTypeEnum getMsgEnum(String code) {
        MsgTypeEnum[] values = MsgTypeEnum.values();
        for (MsgTypeEnum msgType : Arrays.asList(values)) {
            if (msgType.getCode().equals(code)) {
                return msgType;
            }
        }
        return null;
    }


}

package com.andall.sally.supply.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private Long id;

    private Long channelId;

    private String userName;

    private String mobile;

    private Integer checkMobileFlag;

    private Date checkMobileTime;

    private Byte gender;

    private Byte marry;

    private Byte baby;

    private Integer loginStatus;

    private Integer deleteFlag;

    private String remark;

    private Date createTime;

    private Date updateTime;
}
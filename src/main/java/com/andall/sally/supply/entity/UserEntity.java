package com.andall.sally.supply.entity;

import java.util.Date;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getCheckMobileFlag() {
        return checkMobileFlag;
    }

    public void setCheckMobileFlag(Integer checkMobileFlag) {
        this.checkMobileFlag = checkMobileFlag;
    }

    public Date getCheckMobileTime() {
        return checkMobileTime;
    }

    public void setCheckMobileTime(Date checkMobileTime) {
        this.checkMobileTime = checkMobileTime;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Byte getMarry() {
        return marry;
    }

    public void setMarry(Byte marry) {
        this.marry = marry;
    }

    public Byte getBaby() {
        return baby;
    }

    public void setBaby(Byte baby) {
        this.baby = baby;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
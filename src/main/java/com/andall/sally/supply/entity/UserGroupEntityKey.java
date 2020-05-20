package com.andall.sally.supply.entity;

public class UserGroupEntityKey {
    private Long groupId;

    private String regionBlockCode;

    private Long userId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getRegionBlockCode() {
        return regionBlockCode;
    }

    public void setRegionBlockCode(String regionBlockCode) {
        this.regionBlockCode = regionBlockCode == null ? null : regionBlockCode.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
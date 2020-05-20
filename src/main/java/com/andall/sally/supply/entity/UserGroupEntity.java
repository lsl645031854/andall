package com.andall.sally.supply.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UserGroupEntity extends UserGroupEntityKey {
    private Integer restPoint;

    private BigDecimal totalPrice;

    private Integer totalComeShop;

    private Date upgradeDate;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    public Integer getRestPoint() {
        return restPoint;
    }

    public void setRestPoint(Integer restPoint) {
        this.restPoint = restPoint;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalComeShop() {
        return totalComeShop;
    }

    public void setTotalComeShop(Integer totalComeShop) {
        this.totalComeShop = totalComeShop;
    }

    public Date getUpgradeDate() {
        return upgradeDate;
    }

    public void setUpgradeDate(Date upgradeDate) {
        this.upgradeDate = upgradeDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}
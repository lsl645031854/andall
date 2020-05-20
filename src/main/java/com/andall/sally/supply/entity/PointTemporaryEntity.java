package com.andall.sally.supply.entity;

import lombok.Data;

@Data
public class PointTemporaryEntity {
    private Long id;

    private Long groupId;

    private String regionBlockCode;

    private Long userId;

    private Integer totalPoint;

    private Integer clearingPoint;

    private Integer expectRemain;

    private Integer actualRemain;

    private Integer clearedPoint;

    private Integer diff;

    private Byte handleFlag;
}
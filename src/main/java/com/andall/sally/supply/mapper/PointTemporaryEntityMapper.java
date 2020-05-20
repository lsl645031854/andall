package com.andall.sally.supply.mapper;

import com.andall.sally.supply.entity.PointTemporaryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PointTemporaryEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PointTemporaryEntity record);

    int insertSelective(PointTemporaryEntity record);

    PointTemporaryEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PointTemporaryEntity record);

    int updateByPrimaryKey(PointTemporaryEntity record);

    List<PointTemporaryEntity> selectByRegion(@Param("table") String table, @Param("region") String region, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    Integer countByRegion(@Param("table") String table);
}
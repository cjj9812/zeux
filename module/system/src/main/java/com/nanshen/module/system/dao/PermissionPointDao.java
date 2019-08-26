package com.nanshen.module.system.dao;


import com.nanshen.module.system.entity.PermissionPoint;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
  * 企业数据访问接口
  */

@Mapper
@Repository
public interface PermissionPointDao {

    void save(PermissionPoint point);

    void update(PermissionPoint point);

    PermissionPoint findById(String id);

    void deleteById(String id);
}
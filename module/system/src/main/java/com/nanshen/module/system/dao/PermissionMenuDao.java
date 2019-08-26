package com.nanshen.module.system.dao;


import com.nanshen.module.system.entity.PermissionMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
  * 企业数据访问接口
  */
@Mapper
@Repository
public interface PermissionMenuDao{

    void save(PermissionMenu menu);

    void update(PermissionMenu menu);

    PermissionMenu findById(String id);

    void deleteById(String id);
}
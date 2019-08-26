package com.nanshen.module.system.dao;


import com.nanshen.module.system.entity.PermissionApi;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
  * 企业数据访问接口
  */
@Mapper
@Repository
public interface PermissionApiDao{

    void save(PermissionApi api);

    void update(PermissionApi api);

    PermissionApi findById(String id);

    void deleteById(String id);
}
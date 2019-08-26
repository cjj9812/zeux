package com.nanshen.module.system.dao;


import com.nanshen.module.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
  * 企业数据访问接口
  */
@Mapper
@Repository
public interface RoleDao {

    void save(Role role);

    Role selectById(String id);

    Role findById(String id);

    void update(Role target);

    void deleteById(String id);

    List<Role> findAll();

    void assignPerms(@Param("rl") Role role);

    void deleteRolePermission(@Param("roleId") String roleId);

}
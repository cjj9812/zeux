package com.nanshen.module.system.dao;




import com.nanshen.module.system.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Map;

/**
  * 权限数据访问接口
  */

@Mapper
@Repository
public interface PermissionDao{

    List<Permission> findByTypeAndPid(@Param("type") int type, @Param("pid") String pid);

    void save(Permission perm);

    Permission findById(@Param("id") String id);

    void update(Permission permission);

    List<Permission> findAll(Map<String, Object> map);

    void deleteById(Permission permission);

    List<Permission> findRootAll(@Param("pid") String pid);
}
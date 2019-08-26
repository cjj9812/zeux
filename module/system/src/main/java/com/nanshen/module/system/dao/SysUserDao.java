package com.nanshen.module.system.dao;

import com.nanshen.module.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SysUserDao{
	
	int deleteById(String id);
	
	int insert(SysUser record);
    
    SysUser findById(String id);

    int updateByPrimaryKey(SysUser record);

    SysUser findByName(String name);

    List<SysUser> listUser();

    void assignRoles(@Param("su") SysUser user);

    SysUser selectById(String uid);

    List<SysUser> findAll(@Param("map") Map<String, Object> map);
}
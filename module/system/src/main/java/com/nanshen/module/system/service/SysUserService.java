package com.nanshen.module.system.service;

import com.nanshen.module.system.entity.SysUser;

import java.util.List;
import java.util.Map;

public interface SysUserService {

	void save(SysUser sysUser);

	SysUser findById(String id);

	void deleteById(String id);

	void update(SysUser sysUser);

    SysUser findByName(String name);

    List<SysUser> listUser();

    void assignRoles(String userId, List<String> roleIds);

    SysUser selectById(String uid);

    List<SysUser> findAll(Map<String, Object> map);
}
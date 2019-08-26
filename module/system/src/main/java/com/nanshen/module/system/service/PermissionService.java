package com.nanshen.module.system.service;

import com.nanshen.module.system.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService {
    void save(Map<String, Object> map) throws Exception;

    void update(Map<String, Object> map) throws Exception;

    List<Permission> findAll(Map<String, Object> map);

    Map findById(String id) throws Exception;

    void deleteById(String id) throws Exception;

    List<Permission> findRootAll(String pid);

    Permission findById(Permission permission);
}

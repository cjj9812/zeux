package com.nanshen.module.system.service;

import com.nanshen.module.system.entity.Role;

import java.util.List;

public interface RoleService {
    void save(Role role);

    void update(Role role);

    void delete(String id);

    Role findById(String id);

    List<Role> findAll();

    void assignPerms(String roleId, List<String> permIds);
}

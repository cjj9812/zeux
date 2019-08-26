package com.nanshen.module.system.service.impl;


import com.nanshen.common.consts.PermissionConstants;
import com.nanshen.common.utils.IdWorker;
import com.nanshen.module.system.dao.PermissionDao;
import com.nanshen.module.system.dao.RoleDao;
import com.nanshen.module.system.entity.Permission;
import com.nanshen.module.system.entity.Role;
import com.nanshen.module.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色操作业务逻辑层
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 分配权限
     */
    @Transactional
    public void assignPerms(String roleId,List<String> permIds) {
        roleDao.deleteRolePermission(roleId);
        //1.获取分配的角色对象
        Role role = roleDao.selectById(roleId);
        //2.构造角色的权限集合
        Set<Permission> perms = new HashSet<>();
        for (String permId : permIds) {
            Permission permission = permissionDao.findById(permId);
            //需要根据父id和类型查询API权限列表
            List<Permission> apiList = permissionDao.findByTypeAndPid(PermissionConstants.PERMISSION_API, permission.getId());
            perms.addAll(apiList);//自定赋予API权限
            perms.add(permission);//当前菜单或按钮的权限
        }
        //3.设置角色和权限的关系
        role.setPermissions(perms);
        //4.更新角色
        roleDao.deleteRolePermission(roleId);
        roleDao.assignPerms(role);
    }

    /**
     * 添加角色
     */
    public void save(Role role) {
        //填充其他参数
        role.setId(idWorker.nextId() + "");
        roleDao.save(role);
    }

    /**
     * 更新角色
     */
    public void update(Role role) {
        Role target = roleDao.findById(role.getId());
        target.setDescription(role.getDescription());
        target.setName(role.getName());
        roleDao.update(target);
    }

    /**
     * 根据ID查询角色
     */
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    /**
     * 删除角色
     */
    public void delete(String id) {
        roleDao.deleteById(id);
    }



//    public Page<Role> findByPage(String companyId, int page, int size) {
//        return roleDao.findAll(getSpec(companyId), PageRequest.of(page-1, size));
//    }



}

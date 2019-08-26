package com.nanshen.module.system.service.impl;

import com.nanshen.common.utils.IdWorker;
import com.nanshen.common.utils.MD5Util;
import com.nanshen.module.system.dao.RoleDao;
import com.nanshen.module.system.entity.Permission;
import com.nanshen.module.system.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nanshen.module.system.dao.SysUserDao;
import com.nanshen.module.system.entity.SysUser;
import com.nanshen.module.system.service.SysUserService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public void save(SysUser sysUser) {
		// TODO Auto-generated method stub
		//加密密码
		String encrypPsw= MD5Util.strMd5(sysUser.getPassword());
		sysUser.setPassword(encrypPsw);
		sysUser.setId(new IdWorker().nextId());
		sysUserDao.insert(sysUser);
	}

	@Override
	public SysUser findById(String id) {
		// TODO Auto-generated method stub
		SysUser sysUser=sysUserDao.selectById(id);
		if(sysUser==null){return null;}
		Set<Role> roles=sysUser.getRoles();
		Set<Role> roles1=sysUser.getRoles();

		if(roles!=null||roles.size()!=0){
			for (Role role : roles){
				role.setPermissions(null);
				roles1.add(role);
			}
		}
		sysUser.setRoles(roles1);
		return sysUser;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		sysUserDao.deleteById(id);
	}

	@Override
	public void update(SysUser sysUser) {
		// TODO Auto-generated method stub
		String encrypPsw= MD5Util.strMd5(sysUser.getPassword());
		sysUser.setPassword(encrypPsw);
		sysUserDao.updateByPrimaryKey(sysUser);
	}

	@Override
	public SysUser findByName(String name) {
		return sysUserDao.findByName(name);
	}

	@Override
	public List<SysUser> listUser() {
		return sysUserDao.listUser();
	}

	@Override
	public void assignRoles(String userId, List<String> roleIds) {
		//1.根据id查询用户
		SysUser user = sysUserDao.findById(userId);
		//2.设置用户的角色集合
		Set<Role> roles = new HashSet<>();
		for (String roleId : roleIds) {
			Role role = roleDao.selectById(roleId);
			roles.add(role);
		}
		//设置用户和角色集合的关系
		user.setRoles(roles);
		//3.更新用户
		sysUserDao.assignRoles(user);
	}

	@Override
	public SysUser selectById(String uid) {
		return sysUserDao.selectById(uid);
	}

	@Override
	public List<SysUser> findAll(Map<String, Object> map) {
		return sysUserDao.findAll(map);
	}

}

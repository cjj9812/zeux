package com.nanshen.module.system.service.impl;

import com.nanshen.common.consts.PermissionConstants;
import com.nanshen.common.enums.SystemEnum;
import com.nanshen.common.exceptions.SelfException;
import com.nanshen.common.utils.BeanMapUtils;
import com.nanshen.common.utils.IdWorker;
import com.nanshen.module.system.dao.PermissionApiDao;
import com.nanshen.module.system.dao.PermissionDao;
import com.nanshen.module.system.dao.PermissionMenuDao;
import com.nanshen.module.system.dao.PermissionPointDao;
import com.nanshen.module.system.entity.Permission;
import com.nanshen.module.system.entity.PermissionApi;
import com.nanshen.module.system.entity.PermissionMenu;
import com.nanshen.module.system.entity.PermissionPoint;
import com.nanshen.module.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    IdWorker idWorker;

    @Autowired
    PermissionApiDao permissionApiDao;

    @Autowired
    PermissionMenuDao permissionMenuDao;

    @Autowired
    PermissionPointDao permissionPointDao;

    @Autowired
    PermissionDao permissionDao;

    @Override
    public void save(Map<String, Object> map) throws Exception {
        //设置主键的值
        String id = idWorker.nextId()+"";
        //1.通过map构造permission对象
        Permission perm = BeanMapUtils.mapToBean(map,Permission.class);
        perm.setStatus(1);      //启用状态
        perm.setId(id);
        //2.根据类型构造不同的资源对象（菜单，按钮，api）
        int type = perm.getType();
        switch (type) {
            case PermissionConstants.PERMISSION_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map,PermissionMenu.class);
                menu.setId(id);
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.PERMISSION_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map,PermissionPoint.class);
                point.setId(id);
                permissionPointDao.save(point);
                break;
            case PermissionConstants.PERMISSION_API:
                PermissionApi api = BeanMapUtils.mapToBean(map,PermissionApi.class);
                api.setId(id);
                permissionApiDao.save(api);
                break;
            default:
                throw new SelfException(SystemEnum.ERROR);
        }
        //3.保存
        permissionDao.save(perm);
    }

    /**
     * 2.更新权限
     */
    @Override
    public void update(Map<String,Object> map) throws Exception {
        Permission perm = BeanMapUtils.mapToBean(map,Permission.class);
        //1.通过传递的权限id查询权限
        Permission permission = permissionDao.findById(perm.getId());
        permission.setName(perm.getName());
        permission.setCode(perm.getCode());
        permission.setDescription(perm.getDescription());
        permission.setStatus(perm.getStatus());
        //2.根据类型构造不同的资源
        int type = perm.getType();
        switch (type) {
            case PermissionConstants.PERMISSION_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map,PermissionMenu.class);
                menu.setId(perm.getId());
                permissionMenuDao.update(menu);
                break;
            case PermissionConstants.PERMISSION_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map,PermissionPoint.class);
                point.setId(perm.getId());
                permissionPointDao.update(point);
                break;
            case PermissionConstants.PERMISSION_API:
                PermissionApi api = BeanMapUtils.mapToBean(map,PermissionApi.class);
                api.setId(perm.getId());
                permissionApiDao.update(api);
                break;
            default:
                throw new SelfException(SystemEnum.ERROR);
        }
        //3.保存
        permissionDao.update(permission);
    }

    /**
     * 查询全部
     * type      : 查询全部权限列表type：0：菜单 + 按钮（权限点） 1：菜单2：按钮（权限点）3：API接口
     * enVisible : 0：查询所有saas平台的最高权限，1：查询企业的权限
     * pid ：父id
     */
    @Override
    public List<Permission> findAll(Map<String, Object> map) {
        //1.需要查询条件
        Integer type=Integer.parseInt((String) map.get("type"));
        map.put("type",type);
        List<Permission> permissions=permissionDao.findAll(map);
        return permissions;
    }

    /**
     *  根据id查询
     *      //1.查询权限
     *      //2.根据权限的类型查询资源
     *      //3.构造map集合
     */
    public Map<String, Object> findById(String id) throws Exception {
        Permission perm = permissionDao.findById(id);
        if(perm==null) return null;
        int type = perm.getType();

        Object object = null;

        if(type == PermissionConstants.PERMISSION_MENU) {
            object = permissionMenuDao.findById(id);
        }else if (type == PermissionConstants.PERMISSION_POINT) {
            object = permissionPointDao.findById(id);
        }else if (type == PermissionConstants.PERMISSION_API) {
            object = permissionApiDao.findById(id);
        }else {
            throw new SelfException(SystemEnum.ERROR);
        }

        Map<String, Object> map = BeanMapUtils.beanToMap(object);

        map.put("name",perm.getName());
        map.put("type",perm.getType());
        map.put("code",perm.getCode());
        map.put("description",perm.getDescription());
        map.put("pid",perm.getPid());
        return map;
    }

    /**
     * 5.根据id删除
     *  //1.删除权限
     *  //2.删除权限对应的资源
     *
     */
    @Override
    public void deleteById(String id) throws Exception {
        //1.通过传递的权限id查询权限
        Permission permission = permissionDao.findById(id);
        permissionDao.deleteById(permission);
        //2.根据类型构造不同的资源
        int type = permission.getType();
        switch (type) {
            case PermissionConstants.PERMISSION_MENU:
                permissionMenuDao.deleteById(id);
                break;
            case PermissionConstants.PERMISSION_POINT:
                permissionPointDao.deleteById(id);
                break;
            case PermissionConstants.PERMISSION_API:
                permissionApiDao.deleteById(id);
                break;
            default:
                throw new SelfException(SystemEnum.ERROR);
        }
    }

    @Override
    public List<Permission> findRootAll(String pid) {
        return permissionDao.findRootAll(pid);
    }

    public Permission findById(Permission permission){
        return permissionDao.findById(permission.getId());
    }
}

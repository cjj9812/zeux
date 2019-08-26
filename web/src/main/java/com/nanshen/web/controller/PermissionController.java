package com.nanshen.web.controller;


import com.nanshen.common.controller.BaseController;
import com.nanshen.common.enums.SystemEnum;
import com.nanshen.common.vo.ReMessage;
import com.nanshen.component.actionlog.annotation.Log;
import com.nanshen.module.system.entity.Permission;
import com.nanshen.module.system.service.PermissionService;
import com.nanshen.web.enums.PermissionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//1.解决跨域
@CrossOrigin
//2.声明restContoller
@RestController
//3.设置父路径
@RequestMapping(value="/api/v1/pri/permission")
public class PermissionController extends BaseController {
    @Autowired
    private PermissionService permissionService;
    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ReMessage save(@RequestBody Map<String,Object> map) throws Exception {
        permissionService.save(map);
        return success();
    }

    /**
     * 修改
     */
    @Log(type = "BASE")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ReMessage update(@PathVariable(value = "id") String id, @RequestBody Map<String,Object> map) throws Exception {
        //构造id
        map.put("id",id);
        permissionService.update(map);
        return success();
    }

    /**
     * 查询列表
     */
    @Log(type = "BASE")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ReMessage<List> findAll(@RequestParam Map map) {
        List<Permission> list =  permissionService.findAll(map);
        return success(list);
    }

    /**
     * 根据ID查询
     */
    @Log(type = "BASE")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReMessage<Map> findById(@PathVariable(value = "id") String id) throws Exception {
        Map map = permissionService.findById(id);
        if(map==null) return error(PermissionEnum.NOT_EXIST);
        return success(map);
    }



    /**
     * 根据id删除
     */
    @Log(type = "BASE")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ReMessage delete(@PathVariable(value = "id") String id) throws Exception {
        Permission permission=new Permission();
        permission.setId(id);
        Permission permission1=permissionService.findById(permission);
        if (permission1==null) return error(PermissionEnum.NOT_EXIST);
        permissionService.deleteById(id);
        return success();
    }

    /**
     * 查找所有权限
     * @return
     */
    @Log(type = "BASE")
    @GetMapping("/all")
    public ReMessage findAll(){
        List<Permission> permissions=permissionService.findRootAll("1111");
        if (permissions.size()==0) return error(SystemEnum.NO_DATA);
        return success(permissions);
    }


}

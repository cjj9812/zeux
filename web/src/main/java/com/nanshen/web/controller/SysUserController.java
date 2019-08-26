package com.nanshen.web.controller;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanshen.common.vo.PageVO;
import com.nanshen.component.actionlog.annotation.Log;
import com.nanshen.component.excel.ExcelUtil;
import com.nanshen.component.jwt.utils.JwtUtil;
import com.nanshen.module.system.service.SysUserService;
import com.nanshen.module.system.entity.SysUser;

import com.nanshen.common.controller.BaseController;
import com.nanshen.common.vo.ReMessage;
import com.nanshen.module.system.vo.UserVO;
import com.nanshen.web.enums.SysUserEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 描述：SysUser控制层
* 
*/
@RequestMapping("api/v1/pri/sysUser")
@RestController
public class SysUserController extends BaseController{

    @Autowired
    private SysUserService sysUserService;

    /**
    * 描述：根据Id 查询 用户
    */
    @Log(type = "BASE")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReMessage findById(@PathVariable("id") String id)throws Exception {
        SysUser sysUser = sysUserService.findById(id);
        if(sysUser==null) return error(SysUserEnum.NOT_EXIST);
        return success(sysUser);
    }

    /**
    * 描述:创建SysUser
    */
    @Log(type = "BASE")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ReMessage save(@RequestBody @Validated SysUser sysUser){
        SysUser exist=sysUserService.findByName(sysUser.getName());
        if(exist!=null){
            return error(SysUserEnum.EXIST);
        }
        sysUser.setIsDel("false");
        sysUser.setStatus(0);
        sysUser.setCreateTime(System.currentTimeMillis());
    	sysUserService.save(sysUser);
        return success();
    }

    /**
    * 描述：删除SysUser
    * 
    */
    @Log(type = "BASE")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ReMessage deleteById(@PathVariable("id") String id) throws Exception {
        sysUserService.deleteById(id);
        return success();
    }

    /**
    * 描述：更新SysUser
    */
    @Log(type = "BASE")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ReMessage updateSysUser(@PathVariable("id") String id,@RequestBody SysUser sysUser){
        sysUser.setId(id);
        sysUserService.update(sysUser);
        return success();
    }


    /**
     * 导出用户Excel
     */
    @Log(type = "BASE")
    @GetMapping("/export")
    public void exportUser(){
        List<SysUser> userList=sysUserService.listUser();
        ExcelUtil.exportExcel(SysUser.class, userList);
    }


    /**
     * 分配角色
     * @param map
     * @return
     */
    @Log(type = "BASE")
    @PutMapping("/assignRoles")
    public ReMessage assignRoles(@RequestBody Map<String,Object> map){
        //1.获取被分配的用户id
        String userId = (String) map.get("id");
        //2.获取到角色的id列表
        List<String> roleIds = (List<String>) map.get("roleIds");
        //3.调用service完成角色分配
        sysUserService.assignRoles(userId,roleIds);
        return success();

    }

    /**
     * 根据用户token获取权限信息
     * @param token
     * @return
     */
    @Log(type = "BASE")
    @GetMapping("/info")
    public ReMessage info(@RequestHeader(name ="Authorization") String token){
            //1、根据token获取用户id
            String uid=JwtUtil.getId(token);
            SysUser userInfo=sysUserService.selectById(uid);
            if(userInfo==null) return error(SysUserEnum.NOT_EXIST);
            return success(new UserVO(userInfo));
    }



    @GetMapping("/pageList")
    public ReMessage findAll(@RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                             @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
                             @RequestParam(name = "name",required = false) String name){

        Map<String,Object> map =new HashMap<>();
        if(name!=null){map.put("name","%"+name+"%");}
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> sysUsers=sysUserService.findAll(map);
        PageInfo pageInfo=new PageInfo(sysUsers);
        return success(new PageVO<>(pageInfo));
    }

}

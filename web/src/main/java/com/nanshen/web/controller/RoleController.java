package com.nanshen.web.controller;


import com.nanshen.common.controller.BaseController;
import com.nanshen.common.vo.ReMessage;
import com.nanshen.module.system.entity.Role;
import com.nanshen.module.system.service.RoleService;
import com.nanshen.module.system.vo.RoleVO;
import com.nanshen.web.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pri/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 分配权限
     */
    @RequestMapping(value = "/assignPerm", method = RequestMethod.PUT)
    public ReMessage assignPrem(@RequestBody Map<String,Object> map) {
        //1.获取被分配的角色的id
        String roleId = (String) map.get("id");
        //2.获取到权限的id列表
        List<String> permIds = (List<String>) map.get("permIds");
        //3.调用service完成权限分配
        roleService.assignPerms(roleId,permIds);
        return success();
    }


    //添加角色
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ReMessage add(@RequestBody Role role) throws Exception {
        roleService.save(role);
        return success();
    }

    //更新角色
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ReMessage update(@PathVariable(name = "id") String id, @RequestBody Role role) throws Exception {
        Role role1 = roleService.findById(id);
        if(role1==null){
            return error(RoleEnum.NOT_EXIST);
        }
        role.setId(id);
        roleService.update(role);
        return success();
    }

    //删除角色
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ReMessage delete(@PathVariable(name = "id") String id) throws Exception {
        roleService.delete(id);
        return success();
    }

    /**
     * 根据ID获取角色信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReMessage findById(@PathVariable(name = "id") String id){
        Role role = roleService.findById(id);
        if(role==null){
            return error(RoleEnum.NOT_EXIST);
        }
        RoleVO roleVO = new RoleVO(role);
        return success(roleVO);
    }

//    /**
//     * 分页查询角色
//     */
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public ReMessage findByPage(int page,int pagesize,Role role) throws Exception {
//        Page<Role> searchPage = roleService.findByPage( page, pagesize);
//        PageResult<Role> pr = new PageResult(searchPage.getTotalElements(),searchPage.getContent());
//        return new Result(ResultCode.SUCCESS,pr);
//    }

    @RequestMapping(value="/list" ,method=RequestMethod.GET)
    public ReMessage findAll() throws Exception {
        List<Role> roleList = roleService.findAll();
        return success(roleList);
    }



}

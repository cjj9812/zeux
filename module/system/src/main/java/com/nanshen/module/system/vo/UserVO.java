package com.nanshen.module.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nanshen.module.system.entity.Permission;
import com.nanshen.module.system.entity.Role;
import com.nanshen.module.system.entity.SysUser;
import lombok.Data;

import java.util.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVO {
    private String phone;
    private String name;
    private Map<String,Object> roles = new HashMap<>();

    /**
     *
     * @param user
     */
    public UserVO(SysUser user, List<Permission> list) {
        this.name=user.getName();
        this.phone=user.getPhone();

        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();

        for (Permission perm : list) {
            String code = perm.getCode();
            if(perm.getType() == 1) {
                menus.add(code);
            }else if(perm.getType() == 2) {
                points.add(code);
            }else {
                apis.add(code);
            }
        }
        this.roles.put("menus",menus);
        this.roles.put("points",points);
        this.roles.put("apis",apis);
    }


    public UserVO(SysUser user) {
        this.name=user.getName();
        this.phone=user.getPhone();
        Set<Role> roles = user.getRoles();
        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();
        for (Role role : roles) {
            Set<Permission> perms = role.getPermissions();
            for (Permission perm : perms) {
                String code = perm.getCode();
                if(perm.getType() == 1) {
                    menus.add(code);
                }else if(perm.getType() == 2) {
                    points.add(code);
                }else {
                    apis.add(code);
                }
            }
        }
        this.roles.put("menus",menus);
        this.roles.put("points",points);
        this.roles.put("apis",apis);
    }
}

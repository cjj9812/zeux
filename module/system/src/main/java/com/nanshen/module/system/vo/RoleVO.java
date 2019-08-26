package com.nanshen.module.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nanshen.module.system.entity.Permission;
import com.nanshen.module.system.entity.Role;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleVO implements Serializable {
    private String id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 说明
     */
    private String description;


    private List<String> permIds = new ArrayList<>();

    public RoleVO(Role role) {
        BeanUtils.copyProperties(role,this);
        for (Permission perm : role.getPermissions()) {
            this.permIds.add(perm.getId());
        }
    }
}

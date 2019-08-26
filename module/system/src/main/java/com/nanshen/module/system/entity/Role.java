package com.nanshen.module.system.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Role implements Serializable {
    private static final long serialVersionUID = 594829320797158219L;
    private String id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 说明
     */
    private String description;

    private Set<SysUser> users = new HashSet<SysUser>(0);//角色与用户   多对多

    private Set<Permission> permissions = new HashSet<Permission>(0);//角色与模块  多对多
}
package com.nanshen.module.system.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission implements Serializable {
    private static final long serialVersionUID = -4990810027542971546L;
    /**
     * 主键
     */
    private String id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限类型 1为菜单 2为功能 3为API
     */
    private Integer type;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 菜单是否一级菜单（1是，2否）
     */
    private Integer isFirst;

    /**
     * 权限描述
     */
    private List<Permission> permissions;

    /**
     * 权限描述
     */
    private String description;

    private String pid;

    //可见状态
    private Integer status;

    public Permission(String name, Integer type, String code,Integer isFirst, String description) {
        this.name = name;
        this.type = type;
        this.code = code;
        this.description = description;
        this.isFirst=isFirst;
    }

    public Permission(){
        
    }


}
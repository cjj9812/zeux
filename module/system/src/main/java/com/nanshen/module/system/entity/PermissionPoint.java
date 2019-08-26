package com.nanshen.module.system.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * Description: 菜单权限实体类
 */
@Data
public class PermissionPoint implements Serializable {
    private static final long serialVersionUID = -1002411490113957485L;

    /**
     * 主键
     */
    private String id;

    /**
     * 权限代码
     */
    private String pointClass;

    private String pointIcon;

    private String pointStatus;

}
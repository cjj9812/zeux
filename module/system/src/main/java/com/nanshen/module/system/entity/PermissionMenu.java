package com.nanshen.module.system.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 菜单权限实体类
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class PermissionMenu implements Serializable {
    private static final long serialVersionUID = -1002411490113957485L;

    /**
     * 主键
     */
    private String id;

    //展示图标
    private String menuIcon;

    //排序号
    private String menuOrder;
}
package com.nanshen.module.system.entity;

import com.nanshen.component.excel.annotation.Excel;
import com.nanshen.component.excel.enums.ExcelType;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Excel("系统用户")
public class SysUser {
	
/*
*主键id
*/
@Excel(value = "用户ID", type = ExcelType.EXPORT)
private String id;
/*
*用户名
*/
@Excel("姓名")
@NotBlank(message = "姓名不能为空")
private String name;
/*
*密码
*/
@NotBlank(message = "密码不能为空")
@Pattern(regexp = "^[A-Za-z0-9]{8,16}$",message = "密码格式不合法，只能包含8-16数字或字母")
private String password;
/*
*手机
*/
@Pattern(regexp = "^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$",message = "手机格式不对")
@Excel("手机")
private String phone;
/*
*备注
*/
private String remark;
/*
*创建时间
*/
private Long createTime;
/*
*修改时间
*/
private Long updateTime;
/*
*伪删除标记（false为正常，true为删除，默认false）
*/
private String isDel;
/*
*数据状态
*/
private Integer status;

private Set<Role> roles;


}
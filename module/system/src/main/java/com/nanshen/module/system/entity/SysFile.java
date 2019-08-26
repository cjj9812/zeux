package com.nanshen.module.system.entity;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysFile {
	
/*
*主键id
*/
private Long id;
/*
*文件名
*/
private String name;
/*
*文件大小
*/
private Long size;
/*
*文件后缀
*/
private String suffix;
/*
*文件md5
*/
private String md5;
/*
*文件保存路径
*/
private String path;
/*
*上传时间
*/
private Long createTime;
/*
*修改时间
*/
private Long updateTime;
/*
*伪删除标记（默认false）
*/
private String isDel;


}
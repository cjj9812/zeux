package com.nanshen.devtools.core_generator.entity;


import lombok.Data;

/**
 * 数据库字段信息封装类
 * @author Administrator
 *
 */
@Data
public class ColumnClass {

	private String name;
	
	private String annotation;
	
	private String type;
	
	private String changeName;
	
	private Integer primary;	//该状态判定是否为主键，0为主键，other非主键

	
	
}

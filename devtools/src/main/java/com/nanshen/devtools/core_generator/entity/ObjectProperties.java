package com.nanshen.devtools.core_generator.entity;


import lombok.Data;

@Data
public class ObjectProperties {
	
	private String url;
	private String user;
	private String password;
	private String driver;
	private String tableName;
	private String controllerPackage;
	private String modulePackage;
	private String commonPackage;
	
	
}

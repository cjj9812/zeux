package com.nanshen.devtools.core_generator.utils;


import com.nanshen.devtools.core_generator.entity.ObjectProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	public static ObjectProperties resolveProperties() throws IOException{
		File file=new File("devtools/src/main/resources/generator.properties");
		ObjectProperties objectProperties=new ObjectProperties();
		InputStream inputStream=new FileInputStream(file);
		Properties ps = new Properties();
		ps.load(inputStream);
		String p = ps.getProperty("url");
		objectProperties.setUrl(ps.getProperty("url"));
		objectProperties.setModulePackage(ps.getProperty("modulePackage"));
		objectProperties.setCommonPackage(ps.getProperty("commonPackage"));
		objectProperties.setControllerPackage(ps.getProperty("controllerPackage"));
		objectProperties.setTableName(ps.getProperty("tableName"));
		objectProperties.setDriver(ps.getProperty("driver"));
		objectProperties.setUser(ps.getProperty("user"));
		objectProperties.setPassword(ps.getProperty("password"));
		inputStream.close();
		return objectProperties;
	} 
	
	public static void main(String[] args) throws IOException{
		PropertiesUtil.resolveProperties();
	}
}

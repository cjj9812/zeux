package com.nanshen.devtools.core_generator.utils;

import java.util.HashMap;
import java.util.Map;

public class TypeDictionary {

	private static Map<String, Object> typeMap;
	
	static{
		typeMap=new HashMap<>();
		//字符串
		typeMap.put("VARCHAR", "String");
		typeMap.put("CHAR", "String");
		typeMap.put("TEXT", "String");
		
		//整型
		typeMap.put("INT", "Integer");
		typeMap.put("INT UNSIGNED", "Integer");
		typeMap.put("TINYINT", "Integer");
		typeMap.put("TINYINT UNSIGNED", "Integer");
		typeMap.put("SMALLINT", "Integer");
		typeMap.put("SMALLINT UNSIGNED", "Integer");


		//long
		typeMap.put("BIGINT", "Long");
		typeMap.put("BIGINT UNSIGNED", "Long");



		//浮点型
		typeMap.put("DOUBLE", "Double");
		typeMap.put("FLOAT", "Float");
		
		//boolean
		typeMap.put("BIT", "Boolean");
		//时间
		typeMap.put("DATETIME","Date");
	}
	
	public static String getMatchType(String jdbcType){
		return (String) typeMap.get(jdbcType);
	}
}

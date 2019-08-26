package com.nanshen.devtools.core_generator.utils;

import com.nanshen.devtools.core_generator.entity.ColumnClass;
import com.nanshen.devtools.core_generator.entity.ObjectProperties;
import freemarker.template.Template;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CodeGenerateUtils {
	

	private final static String MODULE_PACKAGE="module/system/src/main/java/";
	private final static String COMMOM_PACKAGE="common/src/main/java/";
	private final static String WEB_PACKAGE="web/src/main/java/";

	
	public static void main(String[] args) throws Exception {
		CodeGenerateUtils codeGenerateUtils=new CodeGenerateUtils();
//		codeGenerateUtils.generateController();   //生成controller文件
		codeGenerateUtils.generateService();	  //生成Service接口
		codeGenerateUtils.generateServiceImpl();	//生成Service接口实现类
//		codeGenerateUtils.generateCommon();			//生成controller返回值文件
		codeGenerateUtils.generateRepositoryInterface(codeGenerateUtils.getResultSet());
		codeGenerateUtils.generateEntity(codeGenerateUtils.getResultSet());		//生成entity文件
		codeGenerateUtils.generateMapper(codeGenerateUtils.getResultSet());
	}
	
	
	/**
	 * 创建controller
	 * @throws Exception
	 */
	public void generateController() throws Exception{
		HashMap<String, Object> map = new HashMap<>();
		ObjectProperties op=PropertiesUtil.resolveProperties();
		//解析包名
		String packageName=resolvePackage(op.getControllerPackage());
		//拼装文件路由
		String path=WEB_PACKAGE+packageName+"/controller";
		//检查目录是否存在
		File catagory=new File(path);
		if(!catagory.exists()){
			catagory.mkdirs();
		}	
		map.put("name", upperCase(replaceUnderLineAndUpperCase(op.getTableName())));
		map.put("controllerPackage", op.getControllerPackage());
		map.put("modulePackage", op.getModulePackage());

		//创建文件
		File file=new File(path+"/" + upperCase(replaceUnderLineAndUpperCase(op.getTableName())) + "Controller.java");
		generateFileByTemplate("controller.ftl", file, map);
	}
	
	/**
	 * 创建service
	 * @throws Exception
	 */
	public void generateService() throws Exception{
		HashMap<String, Object> map = new HashMap<>();
		ObjectProperties op=PropertiesUtil.resolveProperties();
		//解析包名
		String packageName=resolvePackage(op.getModulePackage());
		//拼装文件路由
		String path=MODULE_PACKAGE+packageName+"/service";
		//检查目录是否存在
		File catagory=new File(path);
		if(!catagory.exists()){
			catagory.mkdirs();
		}	
		map.put("name", upperCase(replaceUnderLineAndUpperCase(op.getTableName())));
		map.put("packageName", op.getModulePackage());
		//创建文件
		File file=new File(path+"/" + upperCase(replaceUnderLineAndUpperCase(op.getTableName())) + "Service.java");
		generateFileByTemplate("service.ftl", file, map);
	}
	
	/**
	 * 创建service实现类
	 * @throws Exception
	 */
	public void generateServiceImpl() throws Exception{
		HashMap<String, Object> map = new HashMap<>();
		ObjectProperties op=PropertiesUtil.resolveProperties();
		//解析包名
		String packageName=resolvePackage(op.getModulePackage());
		//拼装文件路由
		String path=MODULE_PACKAGE+packageName+"/service/impl";
		//检查目录是否存在
		File catagory=new File(path);
		if(!catagory.exists()){
			catagory.mkdirs();
		}	
		map.put("name", upperCase(replaceUnderLineAndUpperCase(op.getTableName())));
		map.put("packageName", op.getModulePackage());
		//创建文件
		File file=new File(path+"/" + upperCase(replaceUnderLineAndUpperCase(op.getTableName())) + "ServiceImpl.java");
		generateFileByTemplate("serviceImpl.ftl", file, map);
	}


    /**
     * 生成公共类
	 * @throws Exception
	 */
	public void generateCommon() throws Exception{
		HashMap<String, Object> map = new HashMap<>();
		ObjectProperties op=PropertiesUtil.resolveProperties();
		//拼装文件路由
		String path=COMMOM_PACKAGE;
		//检查目录是否存在
		File catagory=new File(path);
		if(!catagory.exists()){
			catagory.mkdirs();
		}	
		map.put("packageName", op.getCommonPackage());
		//创建文件
		File IEnumfile=new File(path+"/enums/" + "IEnum.java");
		File ReMessagefile=new File(path+"/vo/" + "ReMessage.java");
		File SystemEnumfile=new File(path+"/enums/" + "SystemEnum.java");
		File BaseControllerfile=new File(path+"/controller/" + "BaseController.java");
		generateFileByTemplate("IEnum.ftl", IEnumfile, map);
		generateFileByTemplate("ReMessage.ftl", ReMessagefile, map);
		generateFileByTemplate("SystemEnum.ftl", SystemEnumfile, map);
		generateFileByTemplate("BaseController.ftl", BaseControllerfile, map);


	}

	
	
	/**
	 * 生成实体封装类文件
	 * @param resultSet
	 * @throws Exception
	 */
	public void generateEntity(ResultSet resultSet) throws Exception{

		HashMap<String, Object> map = new HashMap<>();
		List<ColumnClass> columnClassList = new ArrayList<>();
		ObjectProperties op=PropertiesUtil.resolveProperties();
		ColumnClass columnClass = null;
		while (resultSet.next()) {
			columnClass = new ColumnClass();
			// 获取字段名称
			columnClass.setName(resultSet.getString("COLUMN_NAME"));
			// 获取字段类型
			columnClass.setType(matchType(resultSet.getString("TYPE_NAME")));
			// 转换字段名称，如 sys_name 变成 SysName
			columnClass.setChangeName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
			// 字段在数据库的注释
			columnClass.setAnnotation(resultSet.getString("REMARKS"));
			columnClassList.add(columnClass);
		}
		map.put("columnList", columnClassList);
		map.put("tableName", upperCase(upperCase(replaceUnderLineAndUpperCase(op.getTableName()))));
		map.put("entityPackageName", op.getModulePackage());

		//解析包名
		String packageName=resolvePackage(op.getModulePackage());
		//拼装文件路由
		String path=MODULE_PACKAGE+packageName+"/entity/";
		File file1=new File(path);
		if(!file1.exists()){
			file1.mkdirs();
		}
		File file = new File(path + upperCase(replaceUnderLineAndUpperCase(op.getTableName())) + ".java");

		generateFileByTemplate("entity.ftl", file, map);
	}

	/**
	 * 生成Dao接口文件
	 * @param resultSet
	 * @throws Exception
	 */
	public void generateRepositoryInterface(ResultSet resultSet) throws Exception{
		HashMap<String, Object> map = new HashMap<>();
		//获取主键信息
		String primaryKey=getPrimaryKey();
		ColumnClass primaryColumn=new ColumnClass();
		while(resultSet.next()){
			if(resultSet.getString("COLUMN_NAME").equals(primaryKey)){
				primaryColumn.setName(resultSet.getString("COLUMN_NAME"));
				primaryColumn.setChangeName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
				primaryColumn.setAnnotation(resultSet.getString("REMARKS"));
				primaryColumn.setType(matchType(resultSet.getString("TYPE_NAME")));
			}
		}
		ObjectProperties op=PropertiesUtil.resolveProperties();
		map.put("primaryColumn", primaryColumn);
		map.put("tableName", upperCase(upperCase(replaceUnderLineAndUpperCase(op.getTableName()))));
		map.put("repositoryPackageName", op.getModulePackage());
		map.put("entityPackageName", op.getModulePackage());
		//解析包名
		String packageName=resolvePackage(op.getModulePackage());
		//拼装文件路由
		String path=MODULE_PACKAGE+packageName+"/dao/";
		File file1=new File(path);
		if(!file1.exists()){
			file1.mkdirs();
		}
		File file = new File(path + upperCase(upperCase(replaceUnderLineAndUpperCase(op.getTableName()))) + "Dao.java");
		generateFileByTemplate("repository.ftl", file, map);
	}


    /**
     * 生成 Mapper xml 文件
	 * @param resultSet
     * @throws Exception
	 */
	public void generateMapper(ResultSet resultSet) throws Exception{
		HashMap<String, Object> map = new HashMap<>();
		//获取主键信息
		String primaryKey=getPrimaryKey();
		ColumnClass primaryColumn=new ColumnClass();
		while(resultSet.next()){
			if(resultSet.getString("COLUMN_NAME").equals(primaryKey)){
				primaryColumn.setName(resultSet.getString("COLUMN_NAME"));
				primaryColumn.setChangeName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
				primaryColumn.setAnnotation(resultSet.getString("REMARKS"));
				primaryColumn.setType(matchType(resultSet.getString("TYPE_NAME")));
			}
		}
		resultSet.beforeFirst();
		//获取非逐渐list
		List<ColumnClass> columnClassList = new ArrayList<>();
		while (resultSet.next()) {
			if (resultSet.getString("COLUMN_NAME").equals("id"))
				continue;
			ColumnClass columnClass = new ColumnClass();
			// 判断是否为主键
			// 获取字段名称
			columnClass.setName(resultSet.getString("COLUMN_NAME"));
			// 获取字段类型
			columnClass.setType(matchType(resultSet.getString("TYPE_NAME")));
			// 转换字段名称，如 sys_name 变成 SysName
			columnClass.setChangeName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
			// 字段在数据库的注释
			columnClass.setAnnotation(resultSet.getString("REMARKS"));
			columnClassList.add(columnClass);
		}
		ObjectProperties op=PropertiesUtil.resolveProperties();

		map.put("primaryColumn", primaryColumn);
		map.put("columnClassList", columnClassList);
		map.put("tableName", op.getTableName());
		map.put("entityName", upperCase(op.getTableName()));
		map.put("repositoryPackageName", op.getModulePackage());
		map.put("entityPackageName", op.getModulePackage());
		//解析包名
		String packageName=resolvePackage(op.getModulePackage());
		//拼装文件路由
		String path=MODULE_PACKAGE+packageName+"/mapper/";
		File file1=new File(path);
		if(!file1.exists()){
			file1.mkdirs();
		}
		File file = new File(path + upperCase(replaceUnderLineAndUpperCase(op.getTableName())) + "Mapper.xml");
		generateFileByTemplate("mapper.ftl", file, map);
	}

	/**
	 * 获取数据库连接
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
		ObjectProperties op=PropertiesUtil.resolveProperties();

		Class.forName(op.getDriver());
	     Connection connection= DriverManager.getConnection(op.getUrl(), op.getUser(), op.getPassword());
	        return connection;
	}


	/**
	 * 获取主键名称
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public String getPrimaryKey() throws SQLException, ClassNotFoundException, IOException {
		ObjectProperties op=PropertiesUtil.resolveProperties();
		Connection connection=getConnection();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet primaryResult=databaseMetaData.getPrimaryKeys(null,null,op.getTableName() );
		String primaryKey=null;
		while (primaryResult.next()) {
			primaryKey=primaryResult.getString("COLUMN_NAME");
		}
		return primaryKey;
	}


	/**
	 * 获取非主键数据库结果集
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ResultSet getResultSet() throws ClassNotFoundException, SQLException, IOException {
		Connection connection=getConnection();
		ObjectProperties op=PropertiesUtil.resolveProperties();
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		//通过DatabaseMetaData可操作数据库元数据
        ResultSet resultSet = databaseMetaData.getColumns(null,"%", op.getTableName(),"%");
        return resultSet;
	}



	/**
	 * 解析包名
	 * @param str
	 * @return
	 */
	public String resolvePackage(String str){
		StringBuffer sb = new StringBuffer(str);
		int count=sb.indexOf(".");
		while(count!=-1){
			sb.replace(count, count+1, "/");
			count=sb.indexOf(".");
		}
		return sb.toString();

	}


	/**
	 * 替换下划线
	 * @param str
	 * @return
	 */
	public String replaceUnderLineAndUpperCase(String str){
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		int count=sb.indexOf("_");
		while(count!=-1){
			sb.replace(count+1,count+2 , sb.substring(count+1, count+2).toUpperCase());
			sb.replace(count, count+1, "");
			count=sb.indexOf("_");
		}
		return sb.toString();

	}

	/**
	 * 首字母大写
	 * @param str
	 * @return
	 */
	public String upperCase(String str){
		String str1=replaceUnderLineAndUpperCase(str);
		StringBuffer result=new StringBuffer(str1);
		result.replace(0, 1, result.substring(0, 1).toUpperCase());
		return result.toString();
	}


	/**
	 * 将指定模版生成制定文件
	 * @param templateName
	 * @param file
	 * @param dataMap
	 * @throws Exception
	 */
	private void generateFileByTemplate(final String templateName,File file,Map<String,Object> dataMap) throws Exception{
		Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
		FileOutputStream fos = new FileOutputStream(file);
		Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
		template.process(dataMap,out);
	}

	/**
	 * 将自断数据库类型转变为对应java类型
	 * @param jdbcType
	 * @return
	 */
	private String matchType(String jdbcType){
		return TypeDictionary.getMatchType(jdbcType);
	}




}

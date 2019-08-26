package ${repositoryPackageName}.dao;

import ${entityPackageName}.entity.${tableName};
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ${tableName}Dao{
	
	int deleteById(${primaryColumn.type} id);
	
	int insert(${tableName} record);
    
    ${tableName} selectById(${primaryColumn.type} id);

    int updateById(${tableName} record);

}
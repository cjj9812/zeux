package com.nanshen.module.system.dao;

import com.nanshen.module.system.entity.SysFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysFileDao{
	
	int deleteByPrimaryKey(Long id);
	
	int insert(SysFile record);
    
    SysFile selectByPrimaryKey(Long id);

    int updateByPrimaryKey(SysFile record);

    SysFile findByMd5(@Param("md5") String md5);
}
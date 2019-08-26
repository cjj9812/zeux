package com.nanshen.module.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nanshen.module.system.dao.SysFileDao;
import com.nanshen.module.system.entity.SysFile;
import com.nanshen.module.system.service.SysFileService;

@Service("sysFileService")
public class SysFileServiceImpl implements SysFileService {

	@Autowired
	private SysFileDao sysFileDao;
	
	@Override
	public void create(SysFile sysFile) {
		// TODO Auto-generated method stub
		sysFileDao.insert(sysFile);
	}

	@Override
	public SysFile findById(Long id) {
		// TODO Auto-generated method stub
		return sysFileDao.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		sysFileDao.deleteByPrimaryKey(id);
	}

	@Override
	public void update(SysFile sysFile) {
		// TODO Auto-generated method stub
		sysFileDao.updateByPrimaryKey(sysFile);
	}

	@Override
	public SysFile findByMd5(String md5) {
		return sysFileDao.findByMd5(md5);
	}

}

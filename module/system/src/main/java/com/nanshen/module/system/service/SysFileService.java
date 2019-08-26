package com.nanshen.module.system.service;

import com.nanshen.module.system.entity.SysFile;

public interface SysFileService {

	void create(SysFile sysFile);

	SysFile findById(Long id);

	void delete(Long id);

	void update(SysFile sysFile);

    SysFile findByMd5(String md5);
}
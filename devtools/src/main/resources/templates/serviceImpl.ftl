package ${packageName}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${packageName}.dao.${name}Dao;
import ${packageName}.entity.${name};
import ${packageName}.service.${name}Service;

@Service("${name?uncap_first}Service")
public class ${name}ServiceImpl implements ${name}Service {

	@Autowired
	private ${name}Dao ${name?uncap_first}Dao;
	
	@Override
	public void create(${name} ${name?uncap_first}) {
		// TODO Auto-generated method stub
		${name?uncap_first}Dao.insert(${name?uncap_first});
	}

	@Override
	public ${name} findById(Long id) {
		// TODO Auto-generated method stub
		return ${name?uncap_first}Dao.selectById(id);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		${name?uncap_first}Dao.deleteById(id);
	}

	@Override
	public void updateById(${name} ${name?uncap_first}) {
		// TODO Auto-generated method stub
		${name?uncap_first}Dao.updateById(${name?uncap_first});
	}

}

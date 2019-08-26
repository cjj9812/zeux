package ${packageName}.service;

import ${packageName}.entity.${name};

public interface ${name}Service {

	void create(${name} ${name?uncap_first});

	${name} findById(Long id);

	void deleteById(Long id);

	void updateById(${name} ${name?uncap_first});

}
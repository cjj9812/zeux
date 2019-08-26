package ${controllerPackage}.controller;
import ${modulePackage}.service.${name}Service;
import ${modulePackage}.entity.${name};

import com.nanshen.common.controller.BaseController;
import com.nanshen.common.vo.ReMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

/**
* 描述：${name}控制层
* 
*/
@RequestMapping("/${name}")
@RestController
public class ${name}Controller extends BaseController{

    @Autowired
    private ${name}Service ${name?uncap_first}Service;

    /**
    * 描述：根据Id 查询
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReMessage findById(@PathVariable("id") Long id)throws Exception {
        ${name} ${name?uncap_first} = ${name?uncap_first}Service.findById(id);
        return success(${name?uncap_first});
    }

    /**
    * 描述:创建${name}
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ReMessage create(@RequestBody ${name} ${name?uncap_first}){
    	${name?uncap_first}Service.create(${name?uncap_first});
        return success();
    }

    /**
    * 描述：删除${name}
    * 
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ReMessage deleteById(@PathVariable("id") Long id) throws Exception {
        ${name?uncap_first}Service.deleteById(id);
        return success();
    }

    /**
    * 描述：更新${name}
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ReMessage update${name}(@PathVariable("id") Long id,@RequestBody ${name} ${name?uncap_first}){
        ${name?uncap_first}.setId(id);
        ${name?uncap_first}Service.updateById(${name?uncap_first});
        return success();
    }

}

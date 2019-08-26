package ${entityPackageName}.entity;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ${tableName} {
	
	<#if columnList ? exists>
        <#list columnList as column>
/*
*${column.annotation}
*/
private ${column.type} ${column.changeName};
        </#list>
    </#if>


}
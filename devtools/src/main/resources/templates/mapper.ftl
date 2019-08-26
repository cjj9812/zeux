<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${repositoryPackageName}.dao.${entityName}Dao">

    <resultMap id="baseMap" type="${entityPackageName}.entity.${entityName}">
        <id column="${primaryColumn.name}" property="${primaryColumn.changeName}"/>
        <#if columnClassList ? exists>
            <#list columnClassList as column>
        <result column="${column.name}" property="${column.changeName}"/>
            </#list>
        </#if>
    </resultMap>


    <select id="selectById" resultMap="baseMap">
        select *
        from ${tableName}
        where ${primaryColumn.name} = ${r"#{id}"};
    </select>


    <insert id="insert" useGeneratedKeys="true" keyProperty="id" >
        insert into
        ${tableName} (<#if columnClassList ? exists>
    <#list columnClassList as column>
        <#if column_has_next=true>
            ${""+column.name},
            <#else >
            ${""+column.name}
        </#if>
    </#list>
            </#if>)
        values (<#if columnClassList ? exists>
    <#list columnClassList as column>
        <#if column_has_next=true>
            ${r"#{"+column.changeName+r"}"},
        <#else >
            ${r"#{"+column.changeName+r"}"}
        </#if>
    </#list>
        </#if>);
    </insert>

    <update id="updateById" parameterType="${entityPackageName}.entity.${entityName}">
        update ${tableName}
        <set>
    <#if columnClassList ? exists>
        <#list columnClassList as column>
            ${r'<if test="' + column.changeName +r'!=null">'}
                ${column.name}=${r"#{"+column.changeName+r"}"},
            </if>
        </#list>
    </#if>
        </set>
        where id=${r"#{id}"};
    </update>
    
    <delete id="deleteById">
        delete from ${tableName} where id=${r"#{id}"};
    </delete>

</mapper>
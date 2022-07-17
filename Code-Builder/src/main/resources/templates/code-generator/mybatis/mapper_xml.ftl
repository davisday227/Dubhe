<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.dao.${classInfo.className}Dao">

    <!-- 插入数据 -->
    <insert id="insert" parameterType="${packageName}.entity.${classInfo.className}Entity">
        INSERT INTO ${classInfo.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
            <#if fieldItem.columnName != "id_" >
            ${r"<if test ='null != "}${fieldItem.fieldName}${r"'>"}
                `${fieldItem.columnName}`<#if fieldItem_has_next>,</#if>
            ${r"</if>"}
            </#if>
            </#list>
            </#if>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
            ${r"<if test ='null != "}${fieldItem.fieldName}${r"'>"}
                ${r"#{"}${fieldItem.fieldName}${r"}"}<#if fieldItem_has_next>,</#if>
            ${r"</if>"}
            </#list>
            </#if>
        </trim>
    </insert>

     <!-- 删除 -->
     <delete id="delete">
        DELETE FROM ${classInfo.tableName}
        WHERE `${classInfo.key.columnName}` =  ${r"#{"}${classInfo.key.fieldName}${r"}"}
     </delete>

      <!-- 更新 -->
      <update id="update" parameterType="${packageName}.entity.${classInfo.className}">
        UPDATE ${classInfo.tableName}
        <set>
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
        <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.fieldName != classInfo.key.fieldName>
            ${r"<if test ='null != "}${fieldItem.fieldName}${r"'>"}`${fieldItem.columnName}` = ${r"#{"}${fieldItem.fieldName}${r"}"}<#if fieldItem_has_next>,</#if>${r"</if>"}
        </#if>
        </#list>
        </#if>
        </set>
        WHERE `${classInfo.key.columnName}` = ${r"#{"}${classInfo.key.fieldName}${r"}"}
      </update>

      <!-- 主键查询 -->
      <select id="selectByKey" resultMap="${packageName}.entity.${classInfo.className}Entity">
        SELECT
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
            ${fieldItem.fieldName} as ${fieldItem.columnName}<#if fieldItem_has_next>,</#if>
            </#list>
        </#if>
        FROM ${classInfo.tableName}
            WHERE `${classInfo.key.columnName}` = ${r"#{"}${classInfo.key.fieldName}${r"}"}
      </select>

    <!-- 分页查询，因为框架会自动加上offer信息，不在这里加，只需要加上自定义的where和order子句 -->
    <select id="selectList" resultMap="${packageName}.entity.${classInfo.className}Entity">
        SELECT
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
            ${fieldItem.fieldName} as ${fieldItem.columnName}<#if fieldItem_has_next>,</#if>
            </#list>
        </#if>
        FROM ${classInfo.tableName}
    </select>

    <!-- 查询是否存在该记录 -->
    <select id="existByKey">
        SELECT count(*)
        FROM ${classInfo.tableName}
        WHERE `${classInfo.key.columnName}` = ${r"#{"}${classInfo.key.fieldName}${r"}"}
    </select>

</mapper>
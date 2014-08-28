<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${packaging}.repository.${className}Dao">

    <insert id="insert" parameterType="${lowerTable}">
        insert into ${tableName}(
        <#list fields as field>
            <#if field.original != "id">
                ${field.original}<#if field_has_next>,</#if>
            </#if>
        </#list>
        ) values (
        <#list fields as field>
            <#if field.lower != "id">
                ${well}{${field.lower}}<#if field_has_next>,</#if>
            </#if>
        </#list>
        )
    </insert>

    <select id="contain" resultType="int">
        <![CDATA[
            select count(*) from ${tableName}
        ]]>
        <where>
            <if test="id != null">
                <![CDATA[
                    and id != ${well}{id}
                ]]>
            </if>
        </where>
    </select>


    <select id="get" parameterType="string" resultType="${lowerTable}" >
        <![CDATA[
			select
			<#list fields as field>
                <#if field.original != "id">
                    ${field.original} ${field.lower}<#if field_has_next>,</#if>
                </#if>
            </#list>
			from ${tableName} where id=${well}{id}
		]]>
    </select>

    <update id="update" parameterType="${lowerTable}">
        update ${tableName}
        <set>
        <#list fields as field>
            <#if field.original != "id">
                 <if test="${field.lower} != null">
                    ${field.original} = ${well}{${field.lower}}<#if field_has_next>,</#if>
                 </if>
            </#if>

        </#list>
        </set>
        where id=${well}{id}
    </update>

     <select id="count" resultType="int">
        <![CDATA[
            select count(*) from ${tableName}
        ]]>
        <where>
            <if test="id != null">
                <![CDATA[
                    id = ${well}{id}
                ]]>
            </if>
        </where>
    </select>

    <select id="find" parameterType="map" resultType="${lowerTable}" >
            <![CDATA[
    			select
                    <#list fields as field>
                         ${field.original}<#if field_has_next>,</#if>
                    </#list>
    			from ${tableName}
    		]]>
            <where>
               <if test="id != null">
                   <![CDATA[
                       id = ${well}{id}
                   ]]>
               </if>
            </where>
            <![CDATA[
    			order by id desc
    		]]>
            <if test="startIndex != null">
                <![CDATA[
                    limit ${well}{startIndex},${well}{pageSize}
                ]]>
            </if>
        </select>

        <delete id="delete" parameterType="list">
            <![CDATA[
                delete from ${tableName} where id in
            ]]>
            <foreach collection="list" item="id"  open="(" separator="," close=")">
                ${well}{id}
            </foreach>
        </delete>
</mapper>
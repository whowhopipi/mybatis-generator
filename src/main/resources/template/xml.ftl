<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${daoPackage}.${daoName}">
	<resultMap id="BaseResultMap" type="${entityPackage}.${entityName}">
	<#list table.columns as ci>
		<#if ci.id>
		<id column="${ci.code}" jdbcType="${ci.jdbcType?upper_case}" property="${ci.name}" />
		<#else>
		<result column="${ci.code}"  jdbcType="${ci.jdbcType?upper_case}" property="${ci.name}" />
		</#if>
	</#list>
	</resultMap>

	<sql id="Base_Column_List">
	${table.columnList}
	</sql>
</mapper>
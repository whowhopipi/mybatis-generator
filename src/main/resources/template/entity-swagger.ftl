package ${entityPackage};

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="${table.common!entityPackage+"."+entityName}")
@Table(name = "${table.code}")
public class ${entityName} implements Serializable {

<#list table.columns as ci>
    @ApiModelProperty(name = "${ci.name}" , value = "${ci.comment}")
    @Column(name = "${ci.code}")
    <#if ci.id>
    @Id
    </#if>
    private ${ci.javaType} ${ci.name};

</#list>
}
	
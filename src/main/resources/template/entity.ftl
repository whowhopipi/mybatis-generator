package ${entityPackage};

import java.io.Serializable;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * ${table.common!}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "${table.code}")
public class ${entityName} implements Serializable {

<#list table.columns as ci>
    /**
     * ${ci.comment}
     */
    @Column(name = "${ci.code}")
    <#if ci.id>
    @Id
    </#if>
    private ${ci.javaType} ${ci.name};

</#list>
}
	
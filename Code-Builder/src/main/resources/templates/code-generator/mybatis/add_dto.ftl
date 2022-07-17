package ${packageName}.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ${classInfo.classComment}新增接口入参
 *
 * @author ${authorName} ${.now?string('yyyy-MM-dd')}
 */
@Data
public class ${classInfo.className}AddDTO {
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
<#if fieldItem.fieldName != classInfo.key.fieldName>

    /**
     * ${fieldItem.fieldComment}
     */
    <#if fieldItem.nullAble == 'NO'>
    @NotBlank(message = "${fieldItem.fieldComment}不允许为空")
    </#if>
    <#if fieldItem.fieldClass == 'String'>
    @Size(max = ${fieldItem.maxLength}, message = "${fieldItem.fieldComment}不允许超过${fieldItem.maxLength}个字符")
    </#if>
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};
</#if>
</#list>
</#if>
}
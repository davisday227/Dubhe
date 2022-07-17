package ${packageName}.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ${classInfo.classComment}更新接口入参
 *
 * @author ${authorName} ${.now?string('yyyy-MM-dd')}
 */
@Data
public class ${classInfo.className}UpdateDTO {
    /**
     * ${classInfo.key.fieldComment}
     */
<#if classInfo.key.fieldClass == 'String'>
    @NotBlank(message = "${classInfo.key.fieldComment}不允许为空")
    @Size(max = ${classInfo.key.maxLength}, message = "${classInfo.key.fieldComment}不允许超过${classInfo.key.maxLength}个字符")
</#if>
    private ${classInfo.key.fieldClass} ${classInfo.key.fieldName};
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
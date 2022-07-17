package ${packageName}.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ${classInfo.classComment}操作接口入参，适用于根据key查询详情和删除接口
 *
 * @author ${authorName} ${.now?string('yyyy-MM-dd')}
 */
@Data
public class ${classInfo.className}OperateDTO {
    /**
     * ${classInfo.key.fieldComment}
     */
<#if classInfo.key.fieldClass == 'String'>
    @NotBlank(message = "${classInfo.key.fieldComment}不允许为空")
    @Size(max = ${classInfo.key.maxLength}, message = "${classInfo.key.fieldComment}不允许超过${classInfo.key.maxLength}个字符")
</#if>
    private ${classInfo.key.fieldClass} ${classInfo.key.fieldName};
}
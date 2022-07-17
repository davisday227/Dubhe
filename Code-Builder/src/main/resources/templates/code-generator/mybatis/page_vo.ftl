package ${packageName}.entity;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * ${classInfo.classComment}
 * @author ${authorName} ${.now?string('yyyy-MM-dd')}
 */
@Data
public class ${classInfo.className}PageQueryVO {
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >

    /**
     * ${fieldItem.fieldComment}
     */
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};
</#list>
</#if>
}
package ${packageName}.dto;

import lombok.Data;

/**
 * ${classInfo.classComment}查询接口入参
 *
 * @author ${authorName} ${.now?string('yyyy-MM-dd')}
 */
@Data
public class ${classInfo.className}QueryDTO {
    /**
     * 查询开始下标
     */
    private int beginNum;

    /**
     * 每页记录数
     */
    private int fetchNum;
}
package ${packageName}.dao;

import ${packageName}.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${classInfo.classComment}Dao
 *
 * @author ${authorName}
 * @date ${.now?string('yyyy/MM/dd')}
 */
public class ${classInfo.className}Dao {

    private static final String ${classInfo.className?upper_case}_NAMESPACE = "${packageName}.dao.${classInfo.className}Dao";

    /**
     * 新增
     **/
    private static final String INSERT = ${classInfo.className?upper_case}_NAMESPACE + ".insert";

    /**
     * 删除
     **/
    private static final String DELETE = ${classInfo.className?upper_case}_NAMESPACE + ".delete";

    /**
     * 更新
     **/
    private static final String UPDATE = ${classInfo.className?upper_case}_NAMESPACE + ".update";

    /**
     * 详情查询
     **/
    private static final String GET_ONE = ${classInfo.className?upper_case}_NAMESPACE + ".selectByKey";

    /**
     * 分页查询
     **/
    private static final String PAGE_QUERY = ${classInfo.className?upper_case}_NAMESPACE + ".selectList";

    /**
     * 查询是否存在该记录
     **/
    private static final String EXIST = ${classInfo.className?upper_case}_NAMESPACE + ".exist";


}
